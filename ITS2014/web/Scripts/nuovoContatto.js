$(function(){
    
    $("#invia").click(function(){
        
        var nuovo_contatto = {
            "cognome": $("#cognome").val()
            , "nome": $("#nome").val()
            , "cellulare": $("#cellulare").val()
            , "email": $("#email").val()
        };
        
        $.post(
                "ContattiSQL"
                , JSON.stringify(nuovo_contatto)
                , function(result){
                    window.location.href = "Contatti.html";
                });
    });
    
});


