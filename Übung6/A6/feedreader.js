/**
 * Sents a POST request to the specified url and calls processResponseXML on
 * success. Data has to be a string just like formatted for GET requests
 * (e.g., "key1=value1&key2=value2"). The value should be passed through
 * encodeURIComponent to encode it for this URI format.
 *
 * If you do not want to specify a function that is called on success, pass an
 * empty function (function() {}).
 *
 * Adjusted for POST requests from unit-de-webis-architectures1.
 */
function postEntry(url, data, encoding, processResponseXML) {
    var req = new XMLHttpRequest();
    if (req) {
        req.onreadystatechange =
            createCallbackFunction(req, processResponseXML);
        req.open("POST", url);
        // Set the Content-Type header field according to the form as well
        req.setRequestHeader('Content-Type', encoding);
        req.send(data);
    }
}

/**
 * Creates a function that checks whether the request has completed successfully
 * and calls the processResponseXML function if so.
 *
 * The generated function can be used as a callback for XMLHttpRequests.
 *
 * Directly copied from unit-de-webis-architectures1.
 */
function createCallbackFunction(req, processResponseXML) {
    return function() {
        // Check whether readyState is complete.
        if (req.readyState == 4) {
            // Check whether server response is O.K.
            if (req.status == 200) {
                processResponseXML(req.responseXML);
            } else {
                alert("HTTP error: " + req.status);
            }
        }
    }
}

/**
 * Can be called to hide or show a certain HTML Element
 */

function Hide() {
    var x = document.getElementById("toggle");
    var y = document.getElementById("hide");
    // show element if it's not visible; change button value
    if (x.style.display === "none") {
        x.style.display = "block";
        y.value = "Hide Form";
    // hide element if it's not visible; change button value
    } else {
        x.style.display = "none";
        y.value = "Show Form";
    }
}
