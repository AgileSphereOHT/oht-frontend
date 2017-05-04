$(document).ready(function(){

    GOVUK.toggle.init();

    // The following JavaScript code is just for demo purposes.
    // Not For Production, please remove when developing the real thing.
    if($('.add-search').length > 0){
        $('.prototype-warning-container').append($('<a href="login" class="log-out-link">Search again</a>'));
    }

    if($('.add-log-out').length > 0){
        $('.prototype-warning-container').append($('<a href="login" class="log-out-link">Log out</a>'));
    }

    var $errorLayer = $('.error-layer'),
        $errSummary = $('.validation-summary'),
        $errMessage = $('.validation-message');

    function displayPageErrors(errorMessageClass){
        $errSummary.removeClass('display-none');
        if(errorMessageClass){
            $('.'+errorMessageClass).removeClass('display-none').parents('.error-layer').addClass('validation-error');
        } else {
            $errMessage.removeClass('display-none');
            $errorLayer.addClass('validation-error');
        }
    }

    // for cleaner urls after using form navigation
    var $jumpForm = $('.jump-form');
    $jumpForm.on('submit', function (evt) {
        evt.preventDefault();
        location.assign($jumpForm.attr('action'));
    });

    // radio buttons
    var $formWithRadioButtons = $('.demo-validation-radio');
    if($formWithRadioButtons.length > 0){
        $formWithRadioButtons.on('submit', function (evt) {
            evt.preventDefault();
            if( $('input:checked').length < 1){
                displayPageErrors();
            } else {
                location.assign($formWithRadioButtons.attr('action'));
            }
        });
    }

    // text type input fields
    var $demoValidationInputsForm = $('.demo-validation-input');
    if($demoValidationInputsForm.length > 0){
        $demoValidationInputsForm.on('submit', function (evt) {
            evt.preventDefault();
            var isValid = true;

            $('input', $demoValidationInputsForm).each(function() {
                if($(this).val() == ""){
                    isValid = false;
                }
            });

            if(isValid){
                location.assign($demoValidationInputsForm.attr('action'));
            } else {
                displayPageErrors();
            }
        });
    }
});