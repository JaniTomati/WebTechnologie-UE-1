// Jana (115753), Christopher (114602), Josef (115850), Jula (115167)
function push() {

	var li = document.createElement("li");
	li.textContent = document.control.pushText.value;
	document.getElementsByTagName("ul")[0].appendChild(li);
 }

 function pop() {

 	var elem_name = document.getElementsByTagName("ul")[0].lastChild.innerHTML;
	document.getElementsByTagName("ul")[0].lastChild.remove();
	document.control.popText.value = elem_name;
 }

function initialize(){

	var push_  = document.getElementById('pushButton');
	push_.addEventListener ('click', push);

	var pop_  = document.getElementById('popButton');
	pop_.addEventListener ('click', pop);
}
