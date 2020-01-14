package lk.PremasiriBrothers.inventorymanagement.asset.process.finance.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lk.PremasiriBrothers.inventorymanagement.asset.process.finance.dao.InvoiceDao;
import lk.PremasiriBrothers.inventorymanagement.asset.process.finance.entity.Invoice;
import lk.PremasiriBrothers.inventorymanagement.asset.process.finance.entity.InvoiceQuantity;
import lk.PremasiriBrothers.inventorymanagement.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.List;

@Service
public class InvoiceService implements AbstractService<Invoice, Integer> {
    private final InvoiceDao invoiceDao;

    @Autowired
    public InvoiceService(InvoiceDao invoiceDao) {
        this.invoiceDao = invoiceDao;
    }

    @Override
    public List<Invoice> findAll() {
        return invoiceDao.findAll();
    }

    @Override
    public Invoice findById(Integer id) {
        return invoiceDao.getOne(id);
    }

    @Override
    public Invoice persist(Invoice invoice) {
        return invoiceDao.save(invoice);
    }

    @Override
    public boolean delete(Integer id) {
        invoiceDao.deleteById(id);
        return false;
    }

    @Override
    public List<Invoice> search(Invoice invoice) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Invoice> invoiceExample = Example.of(invoice, matcher);
        return invoiceDao.findAll(invoiceExample);
    }

    public Invoice lastInvoice() {
        return invoiceDao.findFirstByOrderByIdDesc();
    }


    private void commonTableHeader(PdfPCell pdfPCell) {
        pdfPCell.setBorderColor(BaseColor.BLACK);
        pdfPCell.setPaddingLeft(10);
        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPCell.setBackgroundColor(BaseColor.DARK_GRAY);
        pdfPCell.setExtraParagraphSpace(5f);
    }

    private void commonTableBody(PdfPCell pdfPCell) {
        pdfPCell.setBorderColor(BaseColor.BLACK);
        pdfPCell.setPaddingLeft(10);
        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPCell.setBackgroundColor(BaseColor.WHITE);
        pdfPCell.setExtraParagraphSpace(5f);
    }

    public boolean createPdf(Invoice invoice, ServletContext context, HttpServletRequest request, HttpServletResponse response) {
        Document document = new Document(PageSize.A4, 15, 15, 45, 30);
        try {
            String filePath = context.getRealPath("/resources/report");
            File file = new File(filePath);
            boolean exists = new File(filePath).exists();
            if (!exists) {
                new File(filePath).mkdirs();
            }
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file+"/"+"invoices"+".pdf"));
            document.open();
            Font mainFont = FontFactory.getFont("Arial", 15, BaseColor.BLACK);
            Font priceFont = FontFactory.getFont("Arial", 12, BaseColor.BLACK);
            Font customerFont = FontFactory.getFont("Arial", 10, BaseColor.BLACK);

            Paragraph paragraph = new Paragraph(
                    "Invoice \n" +
                            "Premasiri Brothers \n" +
                    "No.33, Station Road\n" +
                    "Homagama\n" +
                    "TP: 077 9691016 | 077 8921368\n"
                    , mainFont);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setIndentationLeft(50);
            paragraph.setIndentationRight(50);
            paragraph.setSpacingAfter(10);
            document.add(paragraph);

            Paragraph customer = new Paragraph("Customer : "+invoice.getCustomer().getName()+"      "+"Mobile : "+invoice.getCustomer().getMobile()
                    , customerFont);
            customer.setAlignment(Element.ALIGN_LEFT);
            customer.setIndentationLeft(50);
            customer.setIndentationRight(50);
            customer.setSpacingAfter(10);
            document.add(customer);

            PdfPTable table = new PdfPTable(4);//column amount
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10);

            Font tableHeader = FontFactory.getFont("Arial", 12, BaseColor.WHITE);
            Font tableBody = FontFactory.getFont("Arial", 9, BaseColor.BLACK);

            float[] columnWidths = {2f, 2f, 2f, 2f};
            table.setWidths(columnWidths);

            PdfPCell item = new PdfPCell(new Paragraph("Item", tableHeader));
            commonTableHeader(item);
            table.addCell(item);

            PdfPCell unitPrice = new PdfPCell(new Paragraph("Unit Price", tableHeader));
            commonTableHeader(unitPrice);
            table.addCell(unitPrice);

            PdfPCell qty = new PdfPCell(new Paragraph("Quantity", tableHeader));
            commonTableHeader(qty);
            table.addCell(qty);

            PdfPCell amount = new PdfPCell(new Paragraph("Amount", tableHeader));
            commonTableHeader(amount);
            table.addCell(amount);

            for (InvoiceQuantity invoiceQty : invoice.getInvoiceQuantities()) {
                PdfPCell itemName = new PdfPCell(new Paragraph(invoiceQty.getItem().getDescription(), tableBody));
                commonTableBody(itemName);
                table.addCell(itemName);

                PdfPCell itemUnitPrice = new PdfPCell(new Paragraph(invoiceQty.getItem().getSelling().toString(), tableBody));
                commonTableBody(itemUnitPrice);
                table.addCell(itemUnitPrice);

                PdfPCell itemQty = new PdfPCell(new Paragraph(String.valueOf(invoiceQty.getQuantity()), tableBody));
                commonTableBody(itemQty);
                table.addCell(itemQty);


                PdfPCell itemAmount = new PdfPCell(new Paragraph(String.valueOf(invoiceQty.getAmount()), tableBody));
                commonTableBody(itemAmount);
                table.addCell(itemAmount);
            }
            document.add(table);

            Paragraph TotalPrice = new Paragraph(
                    "Total Price :"+invoice.getTotalPrice() +"\n" +
                    "Discount :"+invoice.getDiscountAmount()+"\n" +
                    "Net Amount : "+invoice.getTotalAmount()+"\n" +
                            "Balance Amount : "+invoice.getBalance()+"\n" +
                    "Payed By : "+invoice.getPaymentMethod().getPaymentMethod()+"\n"
                    , priceFont);
            TotalPrice.setAlignment(Element.ALIGN_RIGHT);
            TotalPrice.setIndentationLeft(50);
            TotalPrice.setIndentationRight(50);
            TotalPrice.setSpacingAfter(10);
            document.add(TotalPrice);

            document.close();
            writer.close();
            return true;

        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
            return false;
        }
    }

    public List<Invoice> findByGivenDate(LocalDate currentDate, LocalDate currentDate1) {
        return invoiceDao.findByInvoicedAtBetween(currentDate, currentDate1);
    }
}
