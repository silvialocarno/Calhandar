/**
 *
 */

(function() { // avoid variables ending up in the global scope

  const commitment_type_form = document.getElementById('commitment-type-form');
  const submit_button = document.getElementById('submit-type');

  submit_button.addEventListener('click', (e) => {
    let type = commitment_type_form.querySelector('input[name="commitmentType"]:checked');
    switch(type.value) {
      case 'L':
        document.location.href = 'AddCommitmentPage'
        break;
      case 'M':
        document.location.href = 'Calhandar'
        break;
      case 'P':
        document.location.href = 'Calhandar'
        break;
      default:

    }
  });

})();