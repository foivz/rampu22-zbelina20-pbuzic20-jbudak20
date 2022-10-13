# Inicijalne upute za prijavu projekta iz Razvoja aplikacija za mobilne i pametne uređaje

Poštovane kolegice i kolege, 

čestitamo vam jer ste uspješno prijavili svoj projektni tim na kolegiju Razvoj aplikacija za mobilne i pametne uređaje, te je za vas automatski kreiran repozitorij koji ćete koristiti za verzioniranje vašega koda i za jednostavno dokumentiranje istoga.

Ovaj dokument (README.md) predstavlja **osobnu iskaznicu vašeg projekta**. Vaš prvi zadatak je **prijaviti vlastiti projektni prijedlog** na način da ćete prijavu vašeg projekta, sukladno uputama danim u ovom tekstu, napisati upravo u ovaj dokument, umjesto ovoga teksta.

Za upute o sintaksi koju možete koristiti u ovom dokumentu i kod pisanje vaše projektne dokumentacije pogledajte [ovaj link](https://guides.github.com/features/mastering-markdown/).
Sav programski kod potrebno je verzionirati u glavnoj **master** grani i **obvezno** smjestiti u mapu Software. Sve artefakte (npr. slike) koje ćete koristiti u vašoj dokumentaciju obvezno verzionirati u posebnoj grani koja je već kreirana i koja se naziva **master-docs** i smjestiti u mapu Documentation.

Nakon vaše prijave bit će vam dodijeljen mentor s kojim ćete tijekom semestra raditi na ovom projektu. Mentor će vam slati povratne informacije kroz sekciju Discussions također dostupnu na GitHubu vašeg projekta. A sada, vrijeme je da prijavite vaš projekt. Za prijavu vašeg projektnog prijedloga molimo vas koristite **predložak** koji je naveden u nastavku, a započnite tako da kliknete na *olovku* u desnom gornjem kutu ovoga dokumenta :) 

# Login & Found App

## Projektni tim

Ime i prezime | E-mail adresa (FOI) | JMBAG | Github korisničko ime | Seminarska grupa
------------  | ------------------- | ----- | --------------------- | ----------------
Zvonimir Belina | zbelina20@student.foi.hr | 0016149673 | zbelina20 | G01
Josip Budak | jbudak20@student.foi.hr | 0016147588 | jbudak20 | G01
Patrik Bužić | pbuzic20@student.foi.hr | 0016146757 | pbuzic20 | G01

## Opis domene
Lost & Found App je aplikacija koja će omogućiti ljudima da lakše pronađu svoje izgubljene stvari. Aplikacija je prvotno osmišljena za stanare Studentskog doma Varaždin. 

## Specifikacija projekta
Kako bi korisnik mogao koristiti aplikaciju, mora napraviti svoj korisnički račun. Prijavljivanjem u sustav, korisniku se odmah prikaže lista objava, odnosno stvari koje su izgubljene. Klikom na neku od objava otvaraju mu se detalji vezani uz objavu. Pod detalje se misli kada je stvar izgubljena, gdje je posljednji put viđena ili možda mjesto na kojem je ostavljena. Ukoliko neki od korisnika aplikacije ima informaciju vezanu uz izgubljenu stvar, može komentirati objavu. 
/*Umjesto ovih uputa opišite zahtjeve za funkcionalnošću mobilne aplikacije ili aplikacije za pametne uređaje. Pobrojite osnovne funkcionalnosti i za svaku naznačite ime odgovornog člana tima. Opišite osnovnu buduću arhitekturu programskog proizvoda. Obratite pozornost da mobilne aplikacije često zahtijevaju pozadinske servise. Također uzmite u obzir da bi svaki član tima trebao biti odgovoran za otprilike 3 funkcionalnosti, te da bi opterećenje članova tima trebalo biti ujednačeno. Priložite odgovarajuće dijagrame i skice gdje je to prikladno. Funkcionalnosti sustava bobrojite u tablici ispod koristeći predložak koji slijedi:*/

Oznaka | Naziv | Kratki opis | Odgovorni član tima
------ | ----- | ----------- | -------------------
F01 | Login | Za pristup aplikacija korisnik mora imati vlastiti korisnički račun | Zlatko Stapić
F02 | Pregled objava | Korisnik će imat uvid u sve objave, odnosno izgubljene stvari.  | ...
F03 | CRUD objava | Korisnik će moći kreirati, uređivati te brisati objavu ukoliko je izgubio neku stvar | ----
F04 | Komentiranje objava | Korisnik će imati mogućnost komentiranja objave | ----
F05 | Praćenje objava | Kako bi korisnik vidio je li određena stvar pronađena, korisnik će moći pratiti objavu. | -----

## Tehnologije i oprema
Za izradu same aplikacije koristit ćemo 'Android Studio'. Smatram da nam neće biti potrebna neka dodatna tehnologija.

/*Umjesto ovih uputa jasno popišite sve tehnologije, alate i opremu koju ćete koristiti pri implementaciji vašeg rješenja. Vaše rješenje može biti implementirano u bilo kojoj tehnologiji za razvoj mobilnih aplikacija ili aplikacija za pametne uređaje osim u hibridnim web tehnologijama kao što su React Native ili HTML+CSS+JS. Tehnologije koje ćete koristiti bi trebale biti javno dostupne, a ako ih ne budemo obrađivali na vježbama u vašoj dokumentaciji ćete morati navesti način preuzimanja, instaliranja i korištenja onih tehnologija koje su neopbodne kako bi se vaš programski proizvod preveo i pokrenuo. Pazite da svi alati koje ćete koristiti moraju imati odgovarajuću licencu. Što se tiče zahtjeva nastavnika, obvezno je koristiti git i GitHub za verzioniranje programskog koda, GitHub Wiki za pisanje jednostavne dokumentacije sukladno uputama mentora, a projektne zadatke je potrebno planirati i pratiti u alatu GitHub projects.*/

## Baza podataka i web server
Nastavnici vam mogu pripremiti MySQL bazu podataka i web server na kojem možete postaviti jednostavne php web servise. Ako želite da vam pripremimo ove sustave obavezno to navedite umjesto ovog teksta s napomenom "Trebamo bazu podataka i pristup serveru za PHP skripte". Alternativno, možete koristiti bilo koji online dostupan sustav kao i studentske licence na pojedinim platformama kao što su Heroku ili Azure.

## .gitignore
Uzmite u obzir da je u mapi Software .gitignore konfiguriran za nekoliko tehnologija, ali samo ako će projekti biti smješteni direktno u mapu Software ali ne i u neku pod mapu. Nakon odabira konačne tehnologije i projekta obavezno dopunite/premjestite gitignore kako bi vaš projekt zadovoljavao kriterije koji su opisani u ReadMe.md dokumentu dostupnom u mapi Software.
