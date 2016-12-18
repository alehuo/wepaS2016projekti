#Web-palvelinohjelmoinnin projekti 2016

Kurssin projektityönä on kuvapalvelu, johon käyttäjä voi lisätä kuvia.
Muut käyttäjät pystyvät kommentoimaan ja tykkäämään kuvista sekä selaamaan toisien profiileja.
Sivut on toteutettu responsiivisesti eli ne toimivat sekä selaimessa että mobiililaitteella hyvin.

## Sovelluksen linkki: https://hummingbird-66191.herokuapp.com/

## Toiminnallisuus

* Kuvien lataus
* Kuvien tykkäys ja kommentointi
* Käyttäjäprofiilit
* Rekisteröitymislomake
* Pääkäyttäjällä mahdollisuus poistaa kommentteja ja kuvia hallintasivun kautta
* Responsiivinen ulkoasu eli toimii hyvin sekä mobiililaitteilla että työpöytäselaimilla

## Käyttötapaukset

### 1. Käyttäjä kirjautuu sisään

Käyttäjän navigoidessa sovelluksen sivulle häntä kehotetaan kirjautumaan sisään.

![Kirjautumissivu](kirjautumissivu.png)

### 2. Käyttäjä kirjautuu ulos

Riippuen siitä onko käyttäjällä JavaScript päällä vai ei, klikkaamalla

"Kirjaudu ulos" -nappia navigointipalkin oikeassa yläkulmassa ohjataan käyttäjä

uloskirjautumiseen. Jos JavaScript ei ole päällä, näytetään tämä sivu:

![Uloskirjautuminen](uloskirjautuminen.png)


### 3. Käyttäjä haluaa selata kuvia

Etusivulla näytetään kymmenen uusinta kuvaa. Niitä voi tykätä ja niihin voi kommentoida.

Oletuksena kommenteista näytetään kolme uusinta ja klikkaamalla "Näytä kuvasivu ja loput kommentit" -linkkiä näytetään loput kommentit.

Kuvat ladataan asynkronisesti JavaScriptillä ja niissä hyödynnetään ETag -tunnistetta.

Jos JavaScript ei ole päällä, kuvat latautuvat normaalisti.

Lisäksi käyttäjä voi halutessaan klikata käyttäjien nimiä päästäkseen katsomaan heidän profiilejaan.

![Uloskirjautuminen](syote.png)


### 4. Käyttäjä haluaa jakaa kuvan

Klikkaamalla pyöreää, punaista ![Plus](plusnappi.png) -nappia käyttäjälle näytetään kuvan jakamissivu.

![Kuvan jakaminen 1](kuvan_jakaminen.png)

Kuvan maksimikoko on 12 megatavua ja sallittuja tiedostomuotoja ovat .jpg, .jpeg, .png ja .gif .

Kuvalle annetaan otsikko ja valitaan sopiva kuvatiedosto jonka jälkeen painetaan ![Lataa](lataanappi.png) -nappia.

![Kuvan jakaminen 2](kuvan_jakaminen2.png)

Sen jälkeen painetaan "Lataa" -nappia. Toiminnossa kestää hetki jonka jälkeen käyttäjä

ohjataan sovelluksen etusivulle. Ladattu kuva ilmestyy etusivulle.

### 5. Käyttäjä haluaa hakea muita käyttäjiä

Klikkaamalla "Haku" -nappia navigointipalkista aukeaa hakusivu.

Kenttään syötetään haluttu hakusana ja painetaan "Hae" -nappia.

![Haku 1](haku1.png)

Listaan alapuolelle ilmestyy hakusanaa vastaavat profiilit. Käyttäjätunnusta klikkaamalla pääsee selaamaan käyttäjän profiilia.

![Haku 2](haku2.png)

### 6. Käyttäjä selaa muun käyttäjän profiilia

Käyttäjä voi selata muun käyttäjän profiilia esim. menemällä "Haku" -sivulle tai klikkaamalla

kommenteissa tai kuvissa olevia käyttäjätunnuslinkkiä.

Kuvat järjestetään oletuksena Masonry jQuery -kirjastolla "korttimosaiikiksi".

![Profiili](profiili.png)

Profiilisivulla on sama toiminnallisuus kuin "Syöte" -sivulla; kuvista pystyy tykkäämään ja niitä pystyy kommentoimaan.
Lisäksi on mahdollisuus avata yksittäisen kuvan kuvasivu.

![Kuvasivu](kuvasivu.png)

### 7. Käyttäjä haluaa kommentoida kuvaa

Käyttäjä voi kommentoida kuvaa suoraan klikkaamalla "Kommentoi" -linkkiä kuvakortin alapuolella:

![Kommentointi](kommentointi_animation.gif)

(Loput käyttötapaukset täydentyvät tähän toiseen deadlineen mennessä.)

## Tietokantataulut

* Käyttäjät -taulu
* Kuvat -taulu
* Kuvan data -taulu
* Kommentit -taulu
* Kuvan tykkäykset -liitostaulu
* Kuvan kommentit -liitostaulu


## Testaus

* Yksikkö- ja integraatiotestit jne.
* GitHub -> Travis -> Heroku

## TODO list

* Kuvien tallennus tietokantaan toimii [OK]
* PostgreSQL -tietokantajärjestelmä Herokussa toimii [OK]
* CSRF protection käyttöön (ja tarkista että JavaScript -koodit toimivat tämän kanssa hyvin) [OK]
* Syötteiden validointi [OK, KATSO VIELÄ KUVAN LÄHETYKSEN VALIDOINTI]
* Käyttäjän toimintojen logitus ja seuranta [OK]
* Tykkäys toimii ilman JavaScriptiä [OK]
* Uloskirjautuminen toimii ilman JavaScriptiä [OK]
* Kommentoiti toimii ilman JavaScriptiä [OK]
* Loput testeistä valmiiksi. Nyt testataan ~80% koko sivun toiminnallisuudesta. []

## Toteutetut ominaisuudet:

* Kuvien lataus
* Kuvien tykkäys ja kommentointi
* Käyttäjäprofiilit
* Rekisteröitymislomake
* Responsiivinen ulkoasu eli toimii hyvin sekä mobiililaitteilla että työpöytäselaimilla

## Myöhempään versioon tulossa:

* Pääkäyttäjällä mahdollisuus poistaa kommentteja ja kuvia hallintasivun kautta
* Kuvien pienentäminen tietokantaan vietäessä (Vielä harkinnassa)
* Jos käyttäjä haluaa ladata lisää kuvia näkyville etusivulla, mahdollista se (Vielä harkinnassa)

## NetBeansin liitännäisellä luotu luokkakaavio:

![Luokkadiagrammi](class_diagram.png)

## Alustava tietokantakaavio:

![Tietokantakaavio](https://yuml.me/670dff40)
