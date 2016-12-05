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
            $(this).parent().find(".imgpreloader").hide();
            $(this).fadeIn(1500);
            //Materialbox
            $(this).materialbox();
        });
    });
//    $('.materialboxed')

})

