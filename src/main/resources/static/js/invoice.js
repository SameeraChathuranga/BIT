$(document).ready(function () {
    //data show content show and hide - start
    $("#existingCustomer, #customerDetail, #card, #cash,#customerIdShow").hide();

// new Customer
    $("#btnNewCustomer").bind("click", function () {
        $("#customerDetail").show();
        $("#existingCustomer").hide();
        $("#customerShow").hide();
    });
//existing Customer customer
    $("#btnExistingCustomer").bind("click", function () {
        $("#mobileValue").val("");
        $("#existingCustomer").show();
        $("#customerDetail").hide();
        $("#customerShow").hide();
    });
//existing customer value is adding
    $("#mobileValue").bind("keyup", function () {
        $("#customerDetail").hide();
        $("#customerShow").hide();
        let typedValue = $(this).val();
        if (mobileRegex.test(typedValue)) {
            $("#customerShow").show();
            let findCustomerUrl = $("#customerUrl").val();
            Promise.resolve(getData(findCustomerUrl + typedValue)).then(value => item = value).then(function (value) {
                    // console.log(value);
                    addCustomerRow(value)
                }
            );
        } else {
            $("#customerDetail").hide();
            $("#customerShow").hide();
        }
    });

});
//direct invoice without customer
$('#noCustomer ').change(function () {
    console.log($(this));

    if ($(this).prop("checked") === true) {
        $('#btnNewCustomer,#btnExistingCustomer').prop("disabled", true);
        $("#customerDetail").hide();
        $("#customerShow").hide();
        $("#existingCustomer").hide();
    } else {
        $('#btnNewCustomer,#btnExistingCustomer').prop("disabled", false);
    }

});

/*Customer Model*/
class customer {
    constructor(id, number, name, mobile, email, nic, land, dateOfBirth, gender, title) {
        this._id = id;
        this._number = number;
        this._name = name;
        this._nic = nic;
        this._email = email;
        this._mobile = mobile;
        this._land = land;
        this._dateOfBirth = dateOfBirth
        this._gender = gender;
        this._title = title
    }

    get id() {
        return this._id;
    }

    set id(value) {
        this._id = value;
    }

    get number() {
        return this._number;
    }

    set number(value) {
        this._number = value;
    }

    get name() {
        return this._name;
    }

    set name(value) {
        this._name = value;
    }

    get mobile() {
        return this._mobile;
    }

    set mobile(value) {
        this._mobile = value;
    }

    get email() {
        return this._email;
    }

    set email(value) {
        this._email = value;
    }

    get nic() {
        return this._nic;
    }

    set nic(value) {
        this._nic = value;
    }

    get land() {
        return this._land;
    }

    set land(value) {
        this._land = value;
    }

    get dateOfBirth() {
        return this._dateOfBirth;
    }

    set dateOfBirth(value) {
        this._dateOfBirth = value;
    }

    get gender() {
        return this._gender;
    }

    set gender(value) {
        this._gender = value;
    }

    get title() {
        return this._title;
    }

    set title(value) {
        this._title = value;
    }
}

class item {
    constructor(id, code, description, category, selling
                //  qty, price
    ) {
        this._id = id;
        this._code = code;
        this._description = description;
        this._category = category;
        this._selling = selling;

    }

    get id() {
        return this._id;
    }

    set id(value) {
        this._id = value;
    }

    get code() {
        return this._code;
    }

    set code(value) {
        this._code = value;
    }

    get description() {
        return this._description;
    }

    set description(value) {
        this._description = value;
    }

    get selling() {
        return this._selling;
    }

    set selling(value) {
        this._selling = value;
    }


    get category() {
        return this._category;
    }

    set category(value) {
        this._category = value;
    }
}

function rowDataToLabTest(rowDetails) {
    const labTest = new LabTest();
    for (let i = 0; i <= rowDetails.length; i++) {
        switch (i) {
            case 0:
                labTest.id = rowDetails[i].innerHTML;
                break;
            case 1:
                labTest.code = rowDetails[i].innerHTML;
                break;
            case 2:
                labTest.name = rowDetails[i].innerHTML;
                break;
            case 3:
                labTest.price = rowDetails[i].innerHTML;
                break;
            default:
                return labTest;
        }
    }
}

//SELECTED  TEST
let selectedLabTestArray = [];
//SELECTED  PACKAGE
let selectedMedicalPackageId;
//SELECTED TOTAL PRICE
let totalLabTestPrice;
//selected  package price
let selectedMedicalPackageNameAndPrice;
// get current url
let currentURL = window.location.href;
// regex
let creditVisaCardRegex = /^(?:4[0-9]{12}(?:[0-9]{3})?|[25][1-7][0-9]{14}|6(?:011|5[0-9][0-9])[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|(?:2131|1800|35\d{3})\d{11})$/;

function removeRows(value) {
    let table = document.getElementById(value);
    let rowCount = table.rows.length;
    for (let x = rowCount - 1; x > 0; x--) {
        table.deleteRow(x);
    }
}

function addCustomerRow(customer) {
    let customerTable = document.getElementById("customers");
    removeRows("customers");
    let rowCount = customerTable.rows.length;
    let row = customerTable.insertRow(rowCount);

    for (let i = 0; i < rowCount; i++) {
        row.insertCell(0).innerHTML = customer[i].id;
        row.insertCell(1).innerHTML = customer[i].number;
        row.insertCell(2).innerHTML = customer[i].title;
        row.insertCell(3).innerHTML = customer[i].name;
        row.insertCell(4).innerHTML = customer[i].nic;
        row.insertCell(5).innerHTML = customer[i].dateOfBirth;
        row.insertCell(6).innerHTML = customer[i].gender;
        row.insertCell(7).innerHTML = customer[i].email;
        row.insertCell(8).innerHTML = customer[i].mobile;
        row.insertCell(9).innerHTML = customer[i].land;
        row.insertCell(10).innerHTML = '<button type="button" value="Select" class="btn btn-primary" onClick="selectedCustomer(this)">Select</button>';
    }

}

function selectedCustomer(obj) {
    let index = obj.parentNode.parentNode.rowIndex;
    let customerTable = document.getElementById("customers");
    let array = [];
    let customerSelected = customerTable.rows.item(index).cells;
    for (let cus = 0; cus < customerSelected.length; cus++) {
        array.push(customerSelected[cus].textContent);
    }
    $("#customerDetail").show();

    this.fillCustomerDetailsForm(array);
}

function fillCustomerDetailsForm(customerInArray) {
    for (let i = 0; i < customerInArray.length; i++) {
        switch (i) {
            case 0:
                $("#id").val(customerInArray[i]);
                break;
            case 1:
                $("#number").val(customerInArray[i]);
                break;
            case 2:
                $("#title").val(customerInArray[i]);
                break;
            case 3:
                $("#name").val(customerInArray[i]);
                break;
            case 4:
                $("#nic").val(customerInArray[i]);
                $("#dateOfBirth").val(calculateDateOfBirth(customerInArray[i]));
                $("#gender").val(calculateGender(customerInArray[i]));
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                $("#email").val(customerInArray[i]);
                break;
            case 8:
                $("#mobile").val(customerInArray[i]);
                break;
            case 9:
                $("#land").val(customerInArray[i]);
                break;

        }
    }
}

function selectedItem(obj) {
    let itemIndex = obj.parentNode.parentNode.rowIndex;
    let index = itemIndex - 1;
    console.log("index", itemIndex);
    let itemTable = document.getElementById("myTable");
    let array = [];
    let itemSelected = itemTable.rows.item(itemIndex).cells;
    const item = {};
    for (let n = 0; n < itemSelected.length; n++) {
        array.push(itemSelected[n].textContent);
    }
    console.log(array);

    let selectedItemsTable = document.getElementById("selectedItems");
    let rowCount = selectedItemsTable.rows.length;
    let row = selectedItemsTable.insertRow(rowCount);


    row.insertCell(0).innerHTML = `<span>${array[0]}<input type="hidden" name="invoiceQuantities[${index}].item" value="${array[0]}"/></span>`;
    row.insertCell(1).innerHTML = `<span>${array[1]}</span>`;
    row.insertCell(2).innerHTML = `<span>${array[2]}</span>`;
    row.insertCell(3).innerHTML = `<span>${array[3]}</span>`;
    // row.insertCell(4).innerHTML = `<span>${array[4]}</span>`;
    row.insertCell(4).innerHTML = `<span id="selling">${array[4]}</span>`;
    row.insertCell(5).innerHTML = `<input id="qty" class="form-control" name="invoiceQuantities[${index}].quantity" 
type="text" onkeyup="calculateAmount()">`;
    row.insertCell(6).innerHTML = `<input id="amount" class="form-control" name="invoiceQuantities[${index}].amount" 
readonly type="text">`;
    row.insertCell(7).innerHTML = '<button type="button" class="btn btn-danger" onClick="deleteRow(this)">Remove</button>';


}

function calculateAmount() {

    //<editor-fold desc="Properties need for Calculation">
    let quantity = [];
    let selling = [];
    let t = 0.0;
    let amounts = [];
    let qty = document.querySelectorAll("#qty");
    let sel = document.querySelectorAll("#selling");
    let amnt = document.querySelectorAll("#amount");

    //</editor-fold>

    //<editor-fold desc="Calculate Amount of Other Categories">
    for (let w = 0; w < sel.length; w++) {
        selling.push(parseFloat(sel[w].textContent));
    }

    for (let i = 0; i < qty.length; i++) {
        quantity.push(parseFloat(qty[i].value));
    }

    for (let n = 0; n < qty.length; n++) {
        amnt[n].value = quantity[n] * selling[n];
        if (!isNaN(amnt[n].value)) {
            amounts.push(parseFloat(amnt[n].value));
        } else {
            amnt[n].value = 0.0;
            amounts.push(parseFloat(amnt[n].value));
        }
    }
    //</editor-fold>

    for (let m = 0; m < amounts.length; m++) {
        t = t + amounts[m];
    }
    document.getElementById("totalPrice").value = t.toFixed(2);
    discountedPrice();
    // calculateTotal(amount, amounts);
}

function calculateTotal(amount1, amount2) {
    let t = 0.0;
    let p = 0.0;
    let total = document.getElementById("totalPrice");
    for (let m = 0; m < amount1.length; m++) {
        t = t + amount1[m];
    }
    for (let m = 0; m < amount2.length; m++) {
        p = p + amount2[m];
    }
    total.value = (t + p).toFixed(2);
    discountedPrice();
}

function discountedPrice() {
    if ($("#cmbDiscountRatio option:selected").text() === "No Discount") {
        $("#TotalAmount").val($("#totalPrice").val());
    } else {

        $("#TotalAmount").val($("#totalPrice").val() - ($("#totalPrice").val() * (parseFloat($("#cmbDiscountRatio option:selected").text()) / 100)).toFixed(2));
    }
}

function deleteRow(obj) {
    let i = obj.parentNode.parentNode.rowIndex;
    document.getElementById("selectedItems").deleteRow(i);
    calculateAmount();
}

//balance settlement
$("#amountTendered").on("keyup", function () {
    $("#balance").val($("#amountTendered").val() - $("#TotalAmount").val()).toFixed(2);

    if ($("#balance").val() < 0) {
        backgroundColourChangeBad($(this));
        contentHide(document.getElementById("btnSubmitInvoice"));
    } else {
        backgroundColourChangeGood($(this));
        contentShow(document.getElementById("btnSubmitInvoice"));
        $("#btnSubmitInvoice").attr('class', 'btn btn-success');

    }

});

//discount ratio apply or not
$("#cmbDiscountRatio").on("change", function () {

    discountedPrice();

    if ($("#amountTendered").val() !== "") {

        $("#balance").val($("#amountTendered").val() - $("#amount").val()).toFixed(2);

        if ($("#balance").val() < 0) {
            contentHide(document.getElementById("btnSubmitInvoice"));
        } else {
            contentShow(document.getElementById("btnSubmitInvoice"));
            $("#btnSubmitInvoice").attr('class', 'btn btn-success');

        }
    }
});
//payment method show and hide
$("#cmbPaymentMethod").on("change", function () {
    $("#cardNumber, #bankName").val("");
    console.log("payment", $("#cmbPaymentMethod").val())
    if ($("#cmbPaymentMethod").val() === "CREDITCARD" || $("#cmbPaymentMethod").val() === "CHEQUE") {
        $("#cash").hide();
        $("#card").show();
        $("#amountTendered, #balance").val(" ");
    } else {
        $("#card").hide();
        $("#cash").show();

    }
});
//card number validate
$("#cardNumber").on("keyup", function () {
    if ($("#cmbPaymentMethod").val() === "CREDITCARD") {
        $("#typedLength").html($("#cardNumber").val().length);

        document.getElementById("cardNumber").style.setProperty('background-color', '#ff88b3', 'important');

        if ($("#cardNumber").val().length === 15 && creditVisaCardRegex.test($("#cardNumber").val())) {
            document.getElementById("cardNumber").style.setProperty('background-color', '#7ae899', 'important');
            $("#cardType").html("American Express");
        }

        if ($("#cardNumber").val().length === 16 && creditVisaCardRegex.test($("#cardNumber").val())) {
            document.getElementById("cardNumber").style.setProperty('background-color', '#7ae899', 'important');
            $("#cardType").html("Visa, Master, Discover or JCB");
        }
    }

});

/*//-----------------> Information selection ------ end <----------------------------//*/

/*When click the reset button */
$("#reset").on("click", function () {
    $("#existingCustomer, #customerDetail, #card, #cash,#customerIdShow").hide();
    removeRows("selectedItems");
    removeRows("customers");
    removeMedicalPackageDetail();
    removeLabTestDetail();
    totalLabTestPrice = 0;
    selectedLabTestArray = [];
    contentHide(document.getElementById("labTestShowTable"));
    contentHide(document.getElementById("medicalPackageDetails"));

});

function resetFormAfterPrint() {
    $("#existingCustomer, #customerDetail, #card, #cash,#customerIdShow").hide();
    removeRows("selectedItems");
    removeRows("customers");
}

