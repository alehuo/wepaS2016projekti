#Web-palvelinohjelmoinnin projekti 2016

Kurssin projektityönä on kuvaalvelu, johon käyttäjä voi lisätä kuvia.
Muut käyttäjät pystyvät kommentoimaan ja tykkäämään kuvista sekä selaamaan toisien profiileja.
Sivut on toteutettu responsiivisesti eli ne toimivat sekä selaimessa että mobiililaitteella hyvin.

* Kuvien lataus
* Kuvien tykkäys ja kommentointi
* Käyttäjäprofiilit
* Rekisteröitymislomake
* Responsiivinen ulkoasu eli toimii hyvin sekä mobiililaitteilla että työpöytäselaimilla

Tietokantataulut

* Käyttäjät
* Kuvat
* Kommentit
* Tykkäykset (liitostaulu)


Testaus

* Yksikkö- ja integraatiotestit jne.
* GitHub -> Travis -> Heroku

TODO list

* Kuvien tallennus tietokantaan toimii [OK]
* PostgreSQL -tietokantajärjestelmä Herokussa toimii [OK]
* CSRF protection käyttöön (ja tarkista että JavaScript -koodit toimivat tämän kanssa hyvin) [OK]
* Syötteiden validointi []
* Käyttäjän toimintojen logitus ja seuranta []
* Loput testeistä valmiiksi []