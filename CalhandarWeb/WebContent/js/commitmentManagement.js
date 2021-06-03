/**
 *
 */

(function() { // avoid variables ending up in the global scope

  const commitment_form = document.getElementById('commitment-form');
  const submit_button = document.getElementById('submit-form-button');
  const confirmation_modal = document.getElementById('confirmationModal');

  submit_button.addEventListener('click', (e) => {
    var form = commitment_form;
    if (form.checkValidity()) {
      var self = this;
      makeCall("POST", 'CreateCommitment', form,
          function(req) {
            if (req.readyState === 4) {
              var message = req.responseText;
              if (req.status === 200) {
                var commitment = JSON.parse(req.responseText);
                spawnModal(commitment);
              } else {
                // self.alert.textContent = message;
              }
            }
          }
      );
    } else {
      form.reportValidity();
    }
  });

  function spawnModal(commitment) {
    let popup = $('#confirmationModal');
    let text = 'The event you have added has been confirmed on date ' + commitment.formattedDate + ' from ' + commitment.startTime + ' to ' + commitment.endTime + ' in the physical room ' + commitment.room;
    popup.find('.modal-body').text(text);
    popup.modal('show');
  }

})();