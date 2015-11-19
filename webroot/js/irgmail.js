var irgmail = function(){	
	var configuration;

	var plugins;
	var mails;
	
	//fonction internes
	var initInterface = function(callback){
		
		for (plugin of plugins){
			switch (plugin.type){
				case "button":
					var html = '<button type="button" class="btn btn-primary btn-lg"> <span class="';
					html += plugin.icon;
					html +='"></span>' ;
					$('#buttons').append(html);
					break;
				case "tab" :
					//ajout de l onglet
					
					var label = plugin.label;
					var html = '<li ><a data-toggle="tab" href="#';
					html += label;
					html +=  '">';
					html +=label;
					html += '</a></li>';					
					$("#tabsAnchor").append(html);		
				
					// ajout du panel de l onglet
					html = '<div id="' ;
					html +=label;
					html +='"  class="tab-pane fade in">';			
					$("#tabsContentAnchor").append(html);			
					
					break;					
				default:				
					$("#toolbar").show();
					$("#toolbar").append(plugin.html);					
					break;
			}			
		}				
		//rendre le premier onglet actif
		$("#tabsAnchor li:eq(0) a").tab('show');
		
		callback();
	};
	
	var initMails = function(){
		for (mail of mails){
			var id = mail.tab;
			id = "#" + id;
			var html = '<div class="mail" > <label class="adress">';
			html += mail.adress;
			html += '</label><label class="object">'
			html+= mail.object;
			html+='</label></div>';
			
			$(id).append(html);
		}		
	}
	
	var getMailsHeaders = function(callback){
		/*
		$.getJSON("json/mails.json", function(data){	
			mails = data.mails;
			callback();
		});		*/
		$.get('/mails/', function(array) {
			/*
			$.each(array, function(index, mail) {
			table.append('<tr id="row' + index + '"><td>' + index + '</td><td>' + mail.subject + '</td><td>' + mail.from + '</td></tr>');*/
			
			console.info(array);
       });		
	}
	
	//handlers
	
	var handlerParameters = function(){
		console.info("je clic");
	}
	
	var handlerNewMail = function(){
		console.info(" tu clic");
	}
	
	
	var initEvents = function(){
		/* a debug
		for( plugin of plugins){
			var handler = plugin.handler;
			if (handler ){
				var id = "#" + plugin.label;
				$(id).click(   plugin["handler"]());
			}			
		}*/
	}
	
	//fonction visibles à l extérieur

	var init = function (){
		
		//recuperation des variables pour affichage de l'interface		
		$.getJSON( "json/config.json", function( data ) {	
			configuration = data.configuration;
			plugins = configuration.plugins;
			
			initInterface(initEvents);			
			getMailsHeaders(initMails);	
		
		});
		
		//recuperation des parametres utilisateurs
		
		
		// initialisation de l'interface
		
		// recuperation des mails
		
		//ajout des mails
		
		
	
	};	
	
	return {
		init: init
		
	}
}();