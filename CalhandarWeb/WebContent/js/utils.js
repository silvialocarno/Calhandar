/**
 * AJAX call management
 */

	function makeCall(method, url, formElement, cback, reset = true) {
		var req = new XMLHttpRequest(); // visible by closure
		req.onreadystatechange = function() {
		  cback(req)
		}; // closure
		req.open(method, url);

		if (formElement == null) {
		  req.send();
		} else {
		  req.send(new FormData(formElement));
		}

		if (formElement !== null && reset === true) {
		  formElement.reset();
		}
	}

	function postObjAsJson(method, url, obj, cback) {
		var req = new XMLHttpRequest(); // visible by closure
		req.onreadystatechange = function() {
			cback(req)
		}; // closure
		req.open(method, url);
		req.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
		req.send(JSON.stringify(obj));
	}
