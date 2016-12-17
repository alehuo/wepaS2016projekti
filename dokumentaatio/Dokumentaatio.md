#Web-palvelinohjelmoinnin projekti 2016

Kurssin projektityönä on kuvapalvelu, johon käyttäjä voi lisätä kuvia.
Muut käyttäjät pystyvät kommentoimaan ja tykkäämään kuvista sekä selaamaan toisien profiileja.
Sivut on toteutettu responsiivisesti eli ne toimivat sekä selaimessa että mobiililaitteella hyvin.

Sovelluksen linkki: https://hummingbird-66191.herokuapp.com/

* Kuvien lataus
* Kuvien tykkäys ja kommentointi
* Käyttäjäprofiilit
* Rekisteröitymislomake
* Responsiivinen ulkoasu eli toimii hyvin sekä mobiililaitteilla että työpöytäselaimilla

Tietokantataulut

* Käyttäjät -taulu
* Kuvat -taulu
* Kuvan data -taulu
* Kommentit -taulu
* Kuvan tykkäykset -liitostaulu
* Kuvan kommentit -liitostaulu


Testaus

* Yksikkö- ja integraatiotestit jne.
* GitHub -> Travis -> Heroku

TODO list

* Kuvien tallennus tietokantaan toimii [OK]
* PostgreSQL -tietokantajärjestelmä Herokussa toimii [OK]
* CSRF protection käyttöön (ja tarkista että JavaScript -koodit toimivat tämän kanssa hyvin) [OK]
* Syötteiden validointi [OK, KATSO VIELÄ KUVAN LÄHETYKSEN VALIDOINTI]
* Käyttäjän toimintojen logitus ja seuranta [OK]
* Tykkäys toimii ilman JavaScriptiä [OK]
* Uloskirjautuminen toimii ilman JavaScriptiä [OK]
* Kommentoiti toimii ilman JavaScriptiä []
* Loput testeistä valmiiksi []


[Users]1-*[Images]
[Users]1-*[Images_liked_by]
[Images_liked_by]*-1[Images]
[Images]1-1[File]
[Users]1-*[Users_Comments]
[Users_Comments]*-1[Comments]
[Comments]1-*[Comment_Image]
[Images]1-*[Comment_Image]
[Comment_Image]