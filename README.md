# Inicijalne upute za prijavu projekta iz Razvoja aplikacija za mobilne i pametne uređaje

Poštovane kolegice i kolege, 

čestitamo vam jer ste uspješno prijavili svoj projektni tim na kolegiju Razvoj aplikacija za mobilne i pametne uređaje, te je za vas automatski kreiran repozitorij koji ćete koristiti za verzioniranje vašega koda i za jednostavno dokumentiranje istoga.

Ovaj dokument (README.md) predstavlja **osobnu iskaznicu vašeg projekta**. Vaš prvi zadatak je **prijaviti vlastiti projektni prijedlog** na način da ćete prijavu vašeg projekta, sukladno uputama danim u ovom tekstu, napisati upravo u ovaj dokument, umjesto ovoga teksta.

Za upute o sintaksi koju možete koristiti u ovom dokumentu i kod pisanje vaše projektne dokumentacije pogledajte [ovaj link](https://guides.github.com/features/mastering-markdown/).
Sav programski kod potrebno je verzionirati u glavnoj **master** grani i **obvezno** smjestiti u mapu Software. Sve artefakte (npr. slike) koje ćete koristiti u vašoj dokumentaciju obvezno verzionirati u posebnoj grani koja je već kreirana i koja se naziva **master-docs** i smjestiti u mapu Documentation.

Nakon vaše prijave bit će vam dodijeljen mentor s kojim ćete tijekom semestra raditi na ovom projektu. Mentor će vam slati povratne informacije kroz sekciju Discussions također dostupnu na GitHubu vašeg projekta. A sada, vrijeme je da prijavite vaš projekt. Za prijavu vašeg projektnog prijedloga molimo vas koristite **predložak** koji je naveden u nastavku, a započnite tako da kliknete na *olovku* u desnom gornjem kutu ovoga dokumenta :) 

# Lost & Found App

## Projektni tim

Ime i prezime | E-mail adresa (FOI) | JMBAG | Github korisničko ime | Seminarska grupa
------------  | ------------------- | ----- | --------------------- | ----------------
Zvonimir Belina | zbelina20@student.foi.hr | 0016149673 | zbelina20 | G01
Josip Budak | jbudak20@student.foi.hr | 0016147588 | jbudak20-foi | G01
Patrik Bužić | pbuzic20@student.foi.hr | 0016146757 | pbuzic20 | G01

## Opis domene
Primjetili smo da je proteklih godina broj izgubljenih stvari na području studentskog doma Varaždin izrazito velik. Studenti su za izgubljenu imovinu kreirali objave na Facebook-u, unutar grupe za studentski dom. Objave unutar Facebook grupa su nepregledne, oskudne te su se brzo izgubile među ostalim objavama. Želimo kreirati rješenje ovog problema pod nazivom Lost & Found App (L&F). Lost & Found App (L&F) je aplikacija koja će omogućiti studentima da lakše pronađu svoju izgubljenu imovinu ili pomognu drugima da pronađu izgubljenu imovinu. Aplikacija je prvotno osmišljena za stanare Studentskog doma Varaždin, no u budućnosti bi se mogla proširiti na ostale studentske domove ili veća područja.      

## Specifikacija projekta
Kako bi korisnik mogao koristiti aplikaciju, mora napraviti svoj korisnički račun. Nakon što se korisnik registrirao i prijavio u aplikaciju, na zaslonu mu se prikazuju objave od drugih korisnika. Zaslon ima dva dijela: "Izgubio sam" i "Pronašao sam" koje će moći filtrirati i pretraživati. Nakon otvaranja objave korisnik će ju moći komentirati i kontaktirati objavitelja te objave. Ako se korisnik odluči kreirati svoju objavu, nju će moći izmjenjivati i obrisati.

Oznaka | Naziv | Kratki opis | Odgovorni član tima
------ | ----- | ----------- | -------------------
F01 | Login i registracija | Za pristup aplikacija korisnik mora imati vlastiti korisnički račun | Josip Budak
F02 | Pregledati objave | Korisnik će imati uvid u sve objave, odnosno izgubljene stvari.  | Josip Budak
F03 | Kreirati objave | Korisnik će moći kreirati objavu vezanu uz pronalazak ili gubitak imovine. | Patrik Bužić
F05 | Brisati i ažurirati objave | Korisnik će moći brisati te ažurirati objavu vezanu uz pronalazak ili gubitak imovine. | Josip Budak
F04 | Komentirati objave | Korisnik će imati mogućnost komentiranja objave. | Patrik Bužić
F06 | Notificirati korisnika | Korisnik će dobivati obavijesti (komentar, promjena stanja objave...). | Patrik Bužić
F07 | Filtrirati objave | Korisnik će moći flitrirati sve objave po vrsti imovine. | Zvonimir Belina
F08 | Kontaktirati osobe koja su kreirale objave | Korisnik će moći stupiti u kontakt s osobom koja je kreirala objavu.| Zvonimir Belina
F09 | Pretraživati objave | Korisnik će moći pretraživati objave po nazivu objave.| Zvonimir Belina

## Tehnologije i oprema
Za izradu same aplikacije koristit ćemo 'Android Studio' unutar kojeg je emulator za razne uređaje, pomoću kojeg ćemo testirati aplikaciju. Verzioniranje programskog koda će se provoditi preko git-a i Github-a, a za pohranu podataka korisitmo MySQL bazu podataka. Smatramo da nam neće biti potrebna neka dodatna tehnologija.


## Baza podataka i web server
"Trebamo bazu podataka i pristup serveru za PHP skripte"

## .gitignore
Uzmite u obzir da je u mapi Software .gitignore konfiguriran za nekoliko tehnologija, ali samo ako će projekti biti smješteni direktno u mapu Software ali ne i u neku pod mapu. Nakon odabira konačne tehnologije i projekta obavezno dopunite/premjestite gitignore kako bi vaš projekt zadovoljavao kriterije koji su opisani u ReadMe.md dokumentu dostupnom u mapi Software.
