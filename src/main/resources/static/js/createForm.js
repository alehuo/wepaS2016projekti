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
        modalHeader.textContent = "Kommentoi";

        //Modalin lomake
        var commentForm = document.createElement("form");
        commentForm.id = "commentForm_" + imageUuid;
        commentForm.action = "/comment/" + imageUuid;
        commentForm.method = "POST";

        //Lomakkeen rivi
        var commentFormRow = document.createElement("div");
        commentFormRow.className = "row";

        //Textarean input field
        var textAreaInputField = document.createElement("div");
        textAreaInputField.className = "input-field col s6";

        //Textarean ikoni
        var textAreaIcon = document.createElement("i");
        textAreaIcon.className = "material-icons prefix";
        textAreaIcon.textContent = "mode_edit";

        //Textarean label
        var textAreaLabel = document.createElement("label");
        textAreaLabel.for = "commentModalTextarea_" + imageUuid;
        textAreaLabel.textContent = "Kommentti";

        //Lomakkeen textarea
        var textArea = document.createElement("textarea");
        textArea.id = "commentModalTextarea_" + imageUuid;
        textArea.name = "comment";
        textArea.className = "materialize-textarea";

        var hiddenInput = document.createElement("input");
        hiddenInput.type = "hidden";
        hiddenInput.name = "_csrf";
        hiddenInput.value = $("meta[name='_csrf']").attr("content");

        //Lomakkeen lähetä -nappi
        var submitBtn = document.createElement("a");
        submitBtn.href = "#!";
        submitBtn.id = "commentModalSubmitBtn_" + imageUuid;
        submitBtn.className = "modal-action waves-effect waves-green btn-flat";
        submitBtn.textContent = "Kommentoi";
        $(submitBtn).on("click", function () {
            $(commentForm).submit();
        });

        //Lomakkeen peruuta -nappi
        var cancelBtn = document.createElement("a");
        cancelBtn.href = "#!";
        cancelBtn.id = "commentModalCancelBtn_" + imageUuid;
        cancelBtn.className = "modal-action modal-close waves-effect waves-green btn-flat";
        cancelBtn.textContent = "Peruuta";


        //Input fieldin sisälle ikoni, textarea ja label
        textAreaInputField.appendChild(textAreaIcon);
        textAreaInputField.appendChild(textArea);
        textAreaInputField.appendChild(textAreaLabel);

        //Rivin sisälle inputField
        commentFormRow.appendChild(textAreaInputField);

        //Lisää lomakkeeseen rivi
        commentForm.appendChild(commentFormRow);
        commentForm.appendChild(hiddenInput);

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

        //Lisää modal verkkosivulle
        $(".container").append(elementDiv);
        $('.modal').modal();
        $(elementDiv).modal('open');
    } else {
        $(commentModal).modal('open');
    }
}
