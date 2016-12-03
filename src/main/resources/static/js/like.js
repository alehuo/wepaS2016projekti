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
 * Tykkää kuvasta
 * Tämä metodi ei ole se paras toteutustapa, mutta se toimii :)
 * @param {type} imageId
 * @returns {undefined}
 */
function likeImage(imageUuid) {
    var xmlHttp = new XMLHttpRequest();
    var likeUrl = "/img/like/";
    xmlHttp.open("POST", likeUrl + imageUuid, true);
    xmlHttp.send(null);
    xmlHttp.onreadystatechange = function () {
        if (xmlHttp.readyState == 4) {
            if (xmlHttp.status == 200) {
                var response = xmlHttp.responseText;
                if (response == "like") {
                    Materialize.toast("Tykkäsit kuvasta", 3000);
                    document.getElementById("imageLikes_" + imageUuid).innerHTML++;
                } else if (response == "unlike") {
                    /*Materialize.toast("Kuvan tykkäys poistettu", 3000);*/
                    document.getElementById("imageLikes_" + imageUuid).innerHTML--;
                }
            } else {
                Materialize.toast("Palvelinvirhe", 3000);
            }

        }
    }
    return;
}
