/* 
 * Copyright (C) 2016 alehuo
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * Nämä komennot suoritetaan kun dokumentti on latautunut
 * @type type
 */
$(document).ready(function () {
    //Käynnistä sideNav
    $(".button-collapse").sideNav();
    //Ladataan sivun kuvat asynkronisesti
    $('.autoload').each(function (i, obj) {
        //Preloader esiin
        $(this).parent().find(".imgpreloader").show();
        $(this).hide();
        //Ladataan kuva (Eli siirretään data-original src:hen)
        this.src = $(this).data('original');
        $(this).on('load', function () {
            //Piilota preloader kun kuva on latautunut
            $(this).parent().find(".imgpreloader").hide();
            //Animoi kuva esiin
            $(this).fadeIn(1500);
            //Masonry conffi
            $('.grid').masonry({
                itemSelector: '.grid-item',
                columnWidth: 280,
                isFitWidth: true,
                gutter: 5,
                transitionDuration: '0.6s'
            });
        });
    });
});
/**
 * Tykkäysskripti
 * @param {type} imageUuid
 * @returns {undefined}
 */
$(document.getElementsByClassName("likeForm")).submit(function (event) {
    var imageUuid = $(this).find('input[name="imageUuid"]').val();
    likeImage(imageUuid);
});

/**
 * Tykkää kuvasta
 * @param {type} imageUuid
 * @returns {undefined}
 */
function likeImage(imageUuid) {
    var xmlHttp = new XMLHttpRequest();
    //Pyynnön osoite
    var likeUrl = "/img/like/";
    //Parametrit
    var params = "imageUuid=" + imageUuid + "&redirect=0";
    //Avaa POST -pyyntö
    xmlHttp.open("POST", likeUrl, true);
    //Aseta Content-type
    xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    //Aseta CSRF token
    xmlHttp.setRequestHeader($("meta[name='_csrf_header']").attr("content"), $("meta[name='_csrf']").attr("content"));
    //Lähetä data
    xmlHttp.send(params);
    //Tarkista vastaus
    xmlHttp.onreadystatechange = function () {
        if (xmlHttp.readyState === 4) {
            if (xmlHttp.status === 200) {
                var response = xmlHttp.getResponseHeader("LikeType");
                if (response === "like") {
                    Materialize.toast(likeSuccessText, 3000);
                    document.getElementById("imageLikes_" + imageUuid).innerHTML++;
                } else if (response === "unlike") {
                    document.getElementById("imageLikes_" + imageUuid).innerHTML--;
                }
            } else {
                Materialize.toast(serverError + " (HTTP " + xmlHttp.status + ")", 3000);
            }

        }
    };
    return;
}
;
/**
 * Tämä metodi luo kommentointilomakkeen javascriptin avula
 * @param {type} imageUuid
 * @returns {undefined}
 */
function createCommentModal(imageUuid) {
    var commentModal = document.getElementById("commentModal_" + imageUuid);
    if (commentModal === null) {
        //Elementti
        var elementDiv = document.createElement("div");
        elementDiv.id = "commentModal_" + imageUuid;
        elementDiv.className = "modal";
        //Modalin sisältö
        var modalContentDiv = document.createElement("div");
        modalContentDiv.className = "modal-content";
        //Modalin footer
        var modalFooterDiv = document.createElement("div");
        modalFooterDiv.className = "modal-footer";
        //Otsikko
        var modalHeader = document.createElement("h4");
        modalHeader.textContent = modalCommentTitleText;
        //Modalin lomake
        var commentForm = document.createElement("form");
        commentForm.id = "commentForm_" + imageUuid;
        commentForm.action = "/comment";
        commentForm.method = "POST";
        commentForm.onsubmit = "event.preventDefault();";
        //Lomakkeen rivi
        var commentFormRow = document.createElement("div");
        commentFormRow.className = "row";
        //Textarean input field
        var textAreaInputField = document.createElement("div");
        textAreaInputField.className = "input-field";
        textAreaInputField.style = "width: 98%;";
        //Textarean ikoni
        var textAreaIcon = document.createElement("i");
        textAreaIcon.className = "material-icons prefix";
        textAreaIcon.textContent = "mode_edit";
        //Textarean label
//        var textAreaLabel = document.createElement("label");
//        textAreaLabel.for = "commentModalTextarea_" + imageUuid;
//        textAreaLabel.textContent = "Kommentti";
        //Lomakkeen textarea
        var textArea = document.createElement("textarea");
        textArea.id = "commentModalTextarea_" + imageUuid;
        textArea.name = "comment";
        textArea.className = "materialize-textarea";
        textArea.length = 40;
        textArea.placeholder = modalCaptionText;

        var hiddenInput = document.createElement("input");
        hiddenInput.type = "hidden";
        hiddenInput.name = "_csrf";
        hiddenInput.value = $("meta[name='_csrf']").attr("content");

        var hiddenInput2 = document.createElement("input");
        hiddenInput2.type = "hidden";
        hiddenInput2.name = "imageUuid";
        hiddenInput2.value = imageUuid;

        //Lomakkeen lähetä -nappi
        var submitBtn = document.createElement("a");
        submitBtn.href = "#!";
        submitBtn.id = "commentModalSubmitBtn_" + imageUuid;
        submitBtn.className = "modal-action waves-effect waves-light btn-flat";
        submitBtn.textContent = modalCommentTitleText;

        //Lomakkeen peruuta -nappi
        var cancelBtn = document.createElement("a");
        cancelBtn.href = "#!";
        cancelBtn.id = "commentModalCancelBtn_" + imageUuid;
        cancelBtn.className = "modal-action modal-close waves-effect waves-green btn-flat";
        cancelBtn.textContent = modalCancelText;
        //Input fieldin sisälle ikoni, textarea ja label
        textAreaInputField.appendChild(textAreaIcon);
        textAreaInputField.appendChild(textArea);
//        textAreaInputField.appendChild(textAreaLabel);
        //Rivin sisälle inputField
        commentFormRow.appendChild(textAreaInputField);
        //Lisää lomakkeeseen rivi ja piilotetut kentät
        commentForm.appendChild(commentFormRow);
        commentForm.appendChild(hiddenInput);
        commentForm.appendChild(hiddenInput2);
        //Lisää otsikko sisältöön
        modalContentDiv.appendChild(modalHeader);
        //Lisää lomake sisältöön
        modalContentDiv.appendChild(commentForm);
        //Lisää modal footeriin napit
        modalFooterDiv.appendChild(submitBtn);
        modalFooterDiv.appendChild(cancelBtn);
        //Lisää sisältö elementtiin
        elementDiv.appendChild(modalContentDiv);
        elementDiv.appendChild(modalFooterDiv);

        //Kun Submit -nappia painetaan, luodaan JavaScriptillä POST -pyyntö
        $(submitBtn).on("click", function () {

            //XMLHttpRequest
            var xmlHttp = new XMLHttpRequest();

            //Pyynnön osoite
            var commentUrl = "/comment";

            //Kommentin sisältö
            var comment = $("#commentForm_" + imageUuid).find('textarea[name="comment"]').val();

            //Validoidaan kommentti
            if (comment.length > 0 && comment.length <= 40) {
                //Nykyinen käyttäjä, joka on kirjautunut sisään (Käytetään vain kohdassa jossa lisätään listaan kommentti).
                var currentUser = $("#currentUser").text();
                //Parametrit suoraan lomakkeesta
                var params = $(commentForm).serialize();
                //Avaa POST -pyyntö osoitteeseen
                xmlHttp.open("POST", commentUrl, true);
                //Aseta Content-type
                xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                //Aseta CSRF token
                xmlHttp.setRequestHeader($("meta[name='_csrf_header']").attr("content"), $("meta[name='_csrf']").attr("content"));
                //Lähetä data
                xmlHttp.send(params);
                //Poista käytöstä textarea
                $(textArea).attr("disabled", "disabled");
                //Poista käytöstä lähetä -nappi
                $(submitBtn).attr("disabled", "disabled");
                //Loader
                $(modalContentDiv).append("<div class='preloader-wrapper small active'><div class='spinner-layer spinner-blue-only'><div class='circle-clipper left'><div class='circle'></div></div><div class='gap-patch'><div class='circle'></div></div><div class='circle-clipper right'><div class='circle'></div></div></div></div>");
                //Tarkista vastaus
                xmlHttp.onreadystatechange = function () {
                    if (xmlHttp.readyState === 4) {
                        //Jos kommentin lisäys on OK
                        if (xmlHttp.status === 200) {
                            //Lisää kommentti listaan
                            $("#comments_" + imageUuid).append('<li><p><a href="/profile/' + stripTags(currentUser) + '"><span style="font-weight: bold;">' + stripTags(currentUser) + '</span></a> <span>' + stripTags(comment) + '</span></p></li>');
                            //Kasvata kommenttien määrää
                            var imageCommentCount = document.getElementById("imageComments_" + imageUuid);
                            imageCommentCount.innerHTML++;
                            //Toast -ilmoitus
                            Materialize.toast(commentSuccessText, 3000);
                        } else {
                            //Toast -ilmoitus
                            Materialize.toast(commentFailureText, 3000);
                        }
                        //Sulje modal
                        $(elementDiv).modal('close');
                        //Tyhjää textarea
                        $(textArea).val("");
                        //Tuhoa elementti
                        elementDiv.parentNode.removeChild(elementDiv);
                    }
                };
            } else {
                $(textArea).addClass("invalid");
            }
        });

        //Lisää modal verkkosivulle
        $(document.getElementsByClassName("container")).append(elementDiv);
        $(elementDiv).modal();
        $(textArea).characterCounter();
        $(elementDiv).modal('open');
        $(textArea).focus();
    } else {
        $(commentModal).modal('open');
        $(document.getElementById('commentModalTextarea_' + imageUuid)).focus();
    }
}
;
/* global URL */

/**
 * Näyttää kuvan esikatselun kuvan lataussivulla
 * @param {type} event
 * @returns {undefined}
 */
var previewFile = function (event) {
    var output = document.getElementById('preview');
    if (event.target.files[0] !== null) {
        output.src = URL.createObjectURL(event.target.files[0]);
        $("#preview").fadeIn();
    } else {
        $("#preview").hide();
    }

};
/**
 * Parsii HTML -tagit pois tekstistä
 * @param {type} string
 * @returns {document@call;createElement.innerHTML}
 */
function stripTags(string) {
    var container = document.createElement('div');
    var text = document.createTextNode(string);
    container.appendChild(text);
    return container.innerHTML;
}