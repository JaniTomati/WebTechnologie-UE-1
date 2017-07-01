
$(document).ready(function(){
    $("li").mousedown(function(){
        $(this).toggleClass("selected");
    });

    $("#delete_all").click(function(){
        $("li").remove(".selected");
    });


    $("#make_read").click(function(){  
    	var array_list = $(".selected");
        $("#read_books").append(array_list);
        $(array_list).removeClass("selected");
    });

    $("#make_to_read").click(function(){  
    	var array_list = $(".selected");
        $("#to_read_books").append(array_list);
        $(array_list).removeClass("selected");

    });       
});
