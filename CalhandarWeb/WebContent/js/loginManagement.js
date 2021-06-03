/**
 * Login management
 */

(function() { // avoid variables ending up in the global scope

  const signInButton = document.getElementById('signIn');
  const container = document.getElementById('index-container');

  document.getElementById("login-button").addEventListener('click', (e) => {
    var form = e.target.closest("form");
    if (form.checkValidity()) {
      form.submit();
    } else {
      form.reportValidity();
    }
  });

})();