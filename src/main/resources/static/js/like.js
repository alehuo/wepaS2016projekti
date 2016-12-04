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
 * Tykkäysskripti
 * @param {type} imageUuid
 * @returns {undefined}
 */
function likeImage(imageUuid) {
    var xmlHttp = new XMLHttpRequest();
    //Pyynnön osoite
    var likeUrl = "/img/like/";
    //Parametrit
    var params = "imageUuid=" + imageUuid;
    //Avaa POST -pyyntö
    xmlHttp.open("POST", likeUrl, true);
    //Aseta Content-type
    xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    //Lähetä data
    xmlHttp.send(params);
    //Tarkista vastaus
    xmlHttp.onreadystatechange = function () {
        if (xmlHttp.readyState === 4) {
            if (xmlHttp.status === 200) {
                var response = xmlHttp.responseText;
                if (response === "like") {
                    Materialize.toast("Tykkäsit kuvasta", 3000);
                    document.getElementById("imageLikes_" + imageUuid).innerHTML++;
                } else if (response === "unlike") {
                    document.getElementById("imageLikes_" + imageUuid).innerHTML--;
                }
            } else {
                Materialize.toast("Palvelinvirhe (HTTP " + xmlHttp.status + ")", 3000);
            }

        }
    };
    return;
}
