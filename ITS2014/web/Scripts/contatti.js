
$(function(){
    
    $.getJSON(
            'ContattiSQL'
            , function(result){
                
                var tabella_contatti = $("#contatti > tbody");
                
                $(result).each(function(i, item){
                    var tr_html =
                            "<tr>"
                            + "<td>" + item.cognome + "</td>"
                            + "<td>" + item.nome + "</td>"
                            + "<td>" + item.cellulare + "</td>"
                            + "<td>" + item.email + "</td>"
                            + "<td>" 
                                + "<a href='ModificaContatto.jsp?ContattoId=" 
                                    + item.contatto_id
                                +"'>modifica</a>" + "</td>"
                            + "<td><a class='cancella' href='#'>cancella</a></td>"
                            + "</tr>";
                    tabella_contatti.append(tr_html);
                });
                
              //cancella
                $(".cancella").click(function(e)
                {
                    if (confirm("Sei Sicuro?") == false)
                        return;
                    var contatto_id = $(e.target)
                            .parent()
                            .parent()
                            .attr("data-contatto-id");
                    $.ajax({
                        url: "ContattiSQL?ContattoId=" + contatto_id
                        , type: "DELETE"
                        , data: JSON.stringify({})
                        , success: function(result)
                        {
                            window.location.href = "Contatti.html";
                        }
                    });

                });
            }
    );
});

