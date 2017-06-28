var Rechteck = {
    width: 4,
    length : 5,
    area : function() {
       return this.width*this.length;
    }
};

function initialize(){
	
	Rechteck.width = 5;
	Rechteck.length = 5;
	alert(Rechteck.area());	
}

