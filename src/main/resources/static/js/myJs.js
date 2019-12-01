$(document).ready(function () {

    // set current year to the footer
    document.getElementById("currentYear").innerHTML = new Date().getFullYear();

    /*//Nav bar properties - start//*/
    $('ul.nav li.dropdown').hover(function () {
        $(this).find('.dropdown-menu').stop(true, true).delay(200).fadeIn(10);
    }, function () {
        $(this).find('.dropdown-menu').stop(true, true).delay(200).fadeOut(10);
    });
    /*//Nav bar properties - end//*/

    /* selected field colour and add selected lab test table - start*/
    $('#myTable tbody tr ').bind('click', function (e) {

//todo --> need to create checkbox select or notR

        $(e.currentTarget).children('th').css('background-color', '#00FFFF');

        checkLabTestInArrayOrNot($(e.currentTarget).children('th'));
    });


    /* selected field colour and add selected lab test table - end*/
    /*//--------------- data table short using - data table plugin ------- start //*/
    $("#myTable").DataTable({
        "lengthMenu": [[5, 10, 15, 20, -1], [5, 10, 15, 20, "All"]],
        "ordering": false,
        stateSave: true,
    });
    /*//--------------- data table short using - data table plugin ------- start //*/

    /* Patient and employee Nic Validation - start*/
    $("#nic").bind('keyup', function () {
        let nic = $(this).val();
        $("#dateOfBirth").val(calculateDateOfBirth(nic));
        $("#gender").val(calculateGender(nic));
    });
    /* Patient and employee Nic Validation - end*/

//prevent checkbox==null before submit -start
    $(function () {
        $('#btnSubmit').on("click", function (e) {
            let checked = $(':checkbox:checked').length;
            if (checked === 0) {
                swal("Oops", "At least One Lab Test Should Be Selected!", "error");
                e.preventDefault();
            }
        });
    });
//prevent checkbox==null before submit - end


});
// regex
let nicRegex = /^([0-9]{9}[vV|xX])|^([0-9]{12})$/;
let mobileRegex = /^([0][7][\d]{8}$)|^([7][\d]{8})$/;
let landRegex = /^([0][\d]{9}])|([\d]{9})$/;
let nameRegex = /^[a-zA-Z]{2}[ a-zA-Z]+$/;
let numberRegex = /^([eE][hH][sS][\d]+)$/;
let invoiceNumberRegex = /^[0-9]{10}$/;
let priceRegex = /^[0-9]+[.][0-9][0-9]$/;
let onlyNumberRegex = /^[0-9]*$/;


/*//Nic - data of birth - start//*/
function dateLengthValidate(day) {
    if (day.toLocaleString().length === 1) {
        return day = '0' + day;
    }
    return day;
}

function calculateDateOfBirth(nic) {

    let dateOfBirth = null;
    let day = null;
    let month;
    if (nic.length === 10) {
        day = +nic.substr(2, 3);
        dateOfBirth = '19' + nic.substr(0, 2) + '-';
        if (day > 500) day = day - 500;

        //<editor-fold desc="if else block">
        if (day > 335) {
            day = day - 335;
            day = dateLengthValidate(day);
            month = 12;
        } else if (day > 305) {
            day = day - 305;
            day = dateLengthValidate(day);
            month = 11;
        } else if (day > 274) {
            day = day - 274;
            day = dateLengthValidate(day);
            month = 10;
        } else if (day > 244) {
            day = day - 244;
            day = dateLengthValidate(day);
            month = 9;
        } else if (day > 213) {
            day = day - 213;
            day = dateLengthValidate(day);
            month = 8;
        } else if (day > 182) {
            day = day - 182;
            day = dateLengthValidate(day);
            month = 7;
        } else if (day > 152) {
            day = day - 152;
            day = dateLengthValidate(day);
            month = 6;
        } else if (day > 121) {
            day = day - 121;
            day = dateLengthValidate(day);
            month = 5;
        } else if (day > 91) {
            day = day - 91;
            day = dateLengthValidate(day);
            month = 4;
        } else if (day > 60) {
            day = day - 60;
            day = dateLengthValidate(day);
            month = 3;
        } else if (day < 32) {
            day = day;
            day = dateLengthValidate(day);
            month = 1;
        } else if (day > 31) {
            day = day - 31;
            day = dateLengthValidate(day);
            month = 2;
        }
        //</editor-fold>
        if (month.toLocaleString().length === 2) {

            dateOfBirth = dateOfBirth + month + '-' + day;
        } else {

            dateOfBirth = dateOfBirth + '0' + month + '-' + day;
        }


    } else if (nic.length === 12) {
        dateOfBirth = nic.substr(0, 4) + '-';
        day = +nic.substr(4, 3);
        if (day > 500) day = day - 500;
        //<editor-fold desc="if else block">
        if (day > 335) {
            day = day - 335;
            day = dateLengthValidate(day);
            month = 12;
        } else if (day > 305) {
            day = day - 305;
            day = dateLengthValidate(day);
            month = 11;
        } else if (day > 274) {
            day = day - 274;
            day = dateLengthValidate(day);
            month = 10;
        } else if (day > 244) {
            day = day - 244;
            day = dateLengthValidate(day);
            month = 9;
        } else if (day > 213) {
            day = day - 213;
            day = dateLengthValidate(day);
            month = 8;
        } else if (day > 182) {
            day = day - 182;
            day = dateLengthValidate(day);
            month = 7;
        } else if (day > 152) {
            day = day - 152;
            day = dateLengthValidate(day);
            month = 6;
        } else if (day > 121) {
            day = day - 121;
            day = dateLengthValidate(day);
            month = 5;
        } else if (day > 91) {
            day = day - 91;
            day = dateLengthValidate(day);
            month = 4;
        } else if (day > 60) {
            day = day - 60;
            day = dateLengthValidate(day);
            month = 3;
        } else if (day < 32) {
            day = day;
            day = dateLengthValidate(day);
            month = 1;
        } else if (day > 31) {
            day = day - 31;
            day = dateLengthValidate(day);
            month = 2;
        }
        //</editor-fold>
        if (month.toLocaleString().length === 2) {
            dateOfBirth = dateOfBirth + month + '-' + day;
        } else {
            dateOfBirth = dateOfBirth + '0' + month + '-' + day;
        }
    }
    return dateOfBirth;
}

/*//Nic - data of birth - end//*/

/*//Nic - gender - start//*/
function calculateGender(nic) {
    let gender = null;
    if (nic.length === 10 && nic[9] === "V" || nic[9] === "v" || nic[9] === "x" || nic[9] === "X") {
        if (nic[9] === "v" || nic[9] === "x") {
            swal({
                title: ` Please change "v" or "x" to "V" or "X" `,
                icon: "warning",
            });
        }
        if (+nic.substr(2, 3) < 500) gender = 'MALE';
        else gender = 'FEMALE';

    } else if (nic.length === 12) {
        if (+nic.substr(4, 3) < 500) gender = 'MALE';
        else gender = 'FEMALE';
    }
    return gender;
}

/*//Nic - gender - end//*/

//mobile number and land number validation
$("#mobile").bind("keyup", function () {
    let mobile = $(this).val();
    if (mobileRegex.test(mobile)) {
        backgroundColourChangeGood($(this));
    } else {
        backgroundColourChangeBad($(this));
    }
});
$("#contactMobile").bind("keyup", function () {
    let mobile = $(this).val();
    if (mobileRegex.test(mobile)) {
        backgroundColourChangeGood($(this));
    } else {
        backgroundColourChangeBad($(this));
    }
});
$("#land").bind("keyup", function () {
    let land = $(this).val();
    if (landRegex.test(land)) {
        backgroundColourChangeGood($(this));
    } else {
        backgroundColourChangeBad($(this));
    }
});
//NIC colour change
$("#nic").bind("keyup", function () {
    let nic = $(this).val();
    if (nicRegex.test(nic)) {
        backgroundColourChangeGood($(this));
    } else {
        backgroundColourChangeBad($(this));
    }
});
//Name validation
$("#name").bind("keyup", function () {
    let name = $(this).val();
    if (nameRegex.test(name)) {
        backgroundColourChangeGood($(this));
    } else {
        backgroundColourChangeBad($(this));
    }
});
$("#contactName").bind("keyup", function () {
    let name = $(this).val();
    if (nameRegex.test(name)) {
        backgroundColourChangeGood($(this));
    } else {
        backgroundColourChangeBad($(this));
    }
});
//invoiceNumber validation
$("#invoiceNumber").bind("keyup", function () {
    let invoiceNumber = $(this).val();
    if (invoiceNumberRegex.test(invoiceNumber)) {
        backgroundColourChangeGood($(this));
    } else {
        backgroundColourChangeBad($(this));
    }
});
//patient validation
$("#patientNumber").bind("keyup", function () {
    let patientNumber = $(this).val();
    if (invoiceNumberRegex.test(patientNumber)) {
        backgroundColourChangeGood($(this));
    } else {
        backgroundColourChangeBad($(this));
    }
});

//Price Validation
$("#cost").bind("keyup", function () {
    let cost = $(this).val();
    if (priceRegex.test(cost)) {
        backgroundColourChangeGood($(this));
    } else {
        backgroundColourChangeBad($(this));
    }
});

$("#selling").bind("keyup", function () {
    let selling = $(this).val();
    if (priceRegex.test(selling)) {
        backgroundColourChangeGood($(this));
    } else {
        backgroundColourChangeBad($(this));
    }
});


//colour change function --start
function backgroundColourChangeGood(id) {
    $(id).css('background-color', '#00FFFF');
}

function backgroundColourChangeBad(id) {
    $(id).css('background-color', '#ff99d6');
}

function backgroundColourChangeNothingToChange(id) {
    $(id).css('background-color', '#ffffff');
}

//colour change function -- end

/* some content need to print use this method */

// el (id of content)is variable that need to give when function call
function printContent(el) {
    // restore page = current document
    let restorePage = document.body.innerHTML;
    // print content = need to print area that area must enclosed with div
    document.body.innerHTML = document.getElementById(el).innerHTML;
    //called javascript print function
    window.print();
    //after print set current web page
    document.body.innerHTML = restorePage;
}

//AJAX FUNCTION CALL
async function getData(url) {
    try {
        const result = await fetch(url);
        return await result.json();
    } catch (e) {
        console.log("Error : " + e);
        conformationAndLoginWindow();

    }
}

// conformation message and to login page
function conformationAndLoginWindow() {
    let message = "There is no way to access to the system without  re-login \n Please click \'Ok\' to login";
    swal({
        title: "Attention !",
        icon: "warning",
        text: message,
        buttons: {
            cancel: true,
            confirm: true,
        },
    }).then(value => {
        if (value) {
            let loginUrl = window.location.protocol + "/login";
            window.open(loginUrl, '_self');
        }
    });
}

// content show table show and hide - start
function contentShow(contentName) {
    contentName.removeAttribute("class");
}

function contentHide(contentName) {
    contentName.setAttribute("class", "display");
}

// content show table show and hide - end

//custom invoice search page validation - start
$("#invoiceFindBy").bind("change", function () {
    //set what is the parameter will search
    $("#invoiceFindValue").attr('name', $("#invoiceFindBy").val());
    document.getElementById("invoiceFindValue").style.setProperty('background-color', '#ffffff', 'important');
    $("#invoiceFindValue").val("");
});
$("#invoiceFindValue").bind("keyup", function () {
    let selectedInvoiceSearch = document.getElementById("invoiceFindBy").value;
    let enterValue = $(this).val();
    if (document.getElementById("invoiceFindValue").value.length === 0) {
        backgroundColourChangeNothingToChange($(this));
    } else {
        switch (selectedInvoiceSearch) {
            case ("patient.number") :
                if (numberRegex.test(enterValue)) {
                    backgroundColourChangeGood($(this));
                } else {
                    backgroundColourChangeBad($(this));
                }
                break;
            case ("patient.nic") :
                if (nicRegex.test(enterValue)) {
                    backgroundColourChangeGood($(this));
                } else {
                    backgroundColourChangeBad($(this));
                }
                break;
            case ("patient.mobile") :
                if (mobileRegex.test(enterValue)) {
                    backgroundColourChangeGood($(this));
                } else {
                    backgroundColourChangeBad($(this));
                }
                break;
            case ("patient.name") :
                if (nameRegex.test(enterValue)) {
                    backgroundColourChangeGood($(this));
                } else {
                    backgroundColourChangeBad($(this));
                }
                break;
            case ("number") :
                if (invoiceNumberRegex.test(enterValue)) {
                    backgroundColourChangeGood($(this));
                } else {
                    backgroundColourChangeBad($(this));
                }
                break;
        }
    }
});
//custom invoice search page validation - end

//search form date validation - start
$("#startDate").bind("input", function () {
    let startDate = document.getElementById("startDate").value;
    let milliSecondToDay = Date.parse(new Date());
//only start date has value
    if (startDate.length !== 0) {
        let milliSecondStartDate = Date.parse(startDate);
        if (milliSecondToDay > milliSecondStartDate) {
            backgroundColourChangeGood($(this));
        } else {
            backgroundColourChangeBad($(this));
        }
    } else {
        backgroundColourChangeNothingToChange($(this));
    }
});
$("#endDate").bind("input", function () {
    let endDate = document.getElementById("endDate").value;
    let milliSecondToDay = Date.parse(new Date());
//only start date has value
    if (endDate.length !== 0) {
        let milliSecondStartDate = Date.parse(endDate);
        if (milliSecondToDay > milliSecondStartDate) {
            backgroundColourChangeGood($(this));
        } else {
            backgroundColourChangeBad($(this));
        }
    } else {
        backgroundColourChangeNothingToChange($(this));
    }
});
$("#btnSummaryFind").bind("mouseover", function () {
    let endDate = document.getElementById("endDate").value;
    let startDate = document.getElementById("startDate").value;
    let milliSecondToDay = Date.parse(new Date());
    //if both date filed has some thing
    if (endDate.length !== 0 && startDate.length !== 0) {

        let milliSecondStartDate = Date.parse(startDate);
        let milliSecondEndDate = Date.parse(endDate);

        if (milliSecondToDay < milliSecondStartDate || milliSecondToDay < milliSecondEndDate) {

            swal({
                title: "Date range is not valid",
                icon: "warning",
            });
        }
    } else {
        swal({
            title: "Please re-check date filed",
            icon: "warning",
        });
    }
});
//search form date validation - end

//Supplier find when
$("#supp").bind("change", function () {
    let attrName = $("#name").attr('id');
    let attrCode = $("#code").attr('id');
    if (attrName === 'name') {
        $("#name").val('');
        $("#name").attr('id', 'criteria');
    }
    if (attrCode === 'code') {
        $("#code").val('');
        $("#code").attr('id', 'criteria');
    }
    $("#criteria").attr('name', $(this).val());

});

$("#criteria").bind('keyup', function () {
    let criteriaName = $(this).attr('name');
    if (criteriaName === 'name') {
        $("#criteria").attr('id', 'name');
    }
    if (criteriaName === 'code') {
        $("#criteria").attr('id', 'code');
    }
});

