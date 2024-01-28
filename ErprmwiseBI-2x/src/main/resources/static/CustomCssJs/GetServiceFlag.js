/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var loadCount = 1;

function retrieveServiceFlag(url) {
    var xmlhttp;
    if (window.XMLHttpRequest)
    {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    }
    else
    {// code for IE6, IE5
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange = function ()
    {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            var responseText = xmlhttp.responseText;

            var returnSubProblem = responseText;
            if (returnSubProblem != "")
            {
//        alert(returnSubProblem);
                if (returnSubProblem.trim() == "run") {
                    $("#showtabledata").addClass("ajaxloader");
                    loadCount = 0;
                }
                else {
                    if (loadCount == 0) {
                        $("#showtabledata").removeClass("ajaxloader");
                        loadCount = 1;
                        loadXMLDoc();
                    }
                }

            }
        }
    }
    xmlhttp.open("GET", url, false);
    xmlhttp.send();
}


