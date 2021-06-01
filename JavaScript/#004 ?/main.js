/*
 * 1. Utilizza tutti i metodi di output che conosci per visualizzare il tuo nome e cognome
 */
function es1alert() {
    window.alert("Jacopo Del Granchio");
}

let es1writePremuto = false;
function es1write() {
    if (es1writePremuto) document.write("Jacopo Del Granchio");
    else {
        alert("La pagina sarà cancellata.\nRipremere il bottone per stampare il mio nome.")
        es1writePremuto = true;
    }
}

function es1inner() {
    document.getElementById("nome").innerHTML = "Jacopo Del Granchio";
}

function es1log() {
    console.log("Jacopo Del Granchio");
}

/*
 * 2. Crea una variabile “prezzo”, realizza una funzione che calcoli l’importo dell’IVA (22%),
 *    stampa il “prezzo”, l’IVA ed il prezzo comprensivo di IVA (tutti gli importi devono essere
 *    stampati con 2 cifre decimali)
 */
let prezzo = 200;

function es2() {
    document.getElementById("prezzo").innerHTML = prezzo.toFixed(2);
    document.getElementById("iva").innerHTML = (prezzo * 0.22).toFixed(2);
    document.getElementById("prezzoIVA").innerHTML = (prezzo + prezzo * 0.22).toFixed(2);
}

/*
 * 3. Modifica la variabile “prezzo” e visualizza il nuovo valore ed il tipo della variabile
 */
function es3() {
    let prezzo = "pippo";
    document.getElementById("shadowPrezzo").innerHTML = prezzo;
    document.getElementById("shadowTipo").innerHTML = typeof(prezzo);
}

/*
 * 4. Senza modificare ulteriormente la variabile “prezzo”, visualizza il valore iniziale della
 *    variabile
 */
function es4() {
    document.getElementById("prezzoDopo").innerHTML = prezzo.toFixed(2);
}

/*
 * 5. Crea l’oggetto alunno con le proprietà nome, cognome, classe, sezione ed il metodo
 *    nomeCompleto che contenga nome e cognome. Visualizza il nomeCompleto, la classe e la
 *    sezione utilizzando il metodo che preferisci
 */
let studente = {
    nome: "Jacopo",
    cognome: "Del Granchio",
    classe: 4,
    sezione: "H",
    nomeCompleto: function () { return this.nome + " " + this.cognome; }
}

function es5() {
    document.getElementById("studenteNomeCompleto").innerHTML = studente.nomeCompleto();
    document.getElementById("studenteClasse").innerHTML = studente.classe;
    document.getElementById("studenteSezione").innerHTML = studente.sezione;
}

/*
 * 6. Aggiungi all’oggetto alunno la proprietà “età” e visualizza nella console il nome formato da
 *    lettere maiuscole, il cognome formato da lettere minuscole, il risultato della lunghezza
 *    della stringa nomeCompleto + l’età
 */
function es6() {
    studente.eta = 18;
    console.log(studente.nome.toUpperCase());
    console.log(studente.cognome.toLowerCase());
    console.log((studente.nomeCompleto() + studente.eta).length) // Eta "vale" 2 per la lunghezza, dato che viene sommata come stringa.
}

/*
 * 7. Crea un evento che cancelli la pagina WEB (descrivi nella pagina l’azione da compiere per
 *    cancellare la stessa)
 */
// Nessun JavaScript ulteriore necessario, gia realizzato nel'esercizio 1.

/*
 * 8. Crea un array con 5 elementi e stampa l’array sotto forma di lista non ordinata
 */
let array = [2, 6, 9, 1, 3]

function es8() {
    for (const elemento of array) {
        document.getElementById("array").innerHTML += `<li>${elemento}</li>`;
    }
}

/*
 * 9. Modifica il secondo elemento dell’array ed aggiungi un nuovo elemento, quindi stampa
 *    l’array sotto forma di lista ordinata
 */
function es9() {
    array[1] = "pippo";
    array.push("pluto");

    for (const elemento of array) {
        document.getElementById("arrayOrdinato").innerHTML += `<li>${elemento}</li>`;
    }
}

/*
 * 10. Crea un array chiamato supermercato formato da 3 array (alimentari, igieneCasa e igienePersona), ciascun array deve contenere almeno 3 elementi.
 *     Crea un testo predefinito che dovrà essere modificato tramite 6 pulsanti che eseguono i seguenti eventi:
 *       a) Cliccando sul primo pulsante vengono visualizzati tutti i prodotti del supermercato
 *       b) Cliccando sul secondo pulsante vengono visualizzati solo i prodotti alimentari
 *       c) Cliccando sul terzo pulsante vengono visualizzati solo i prodotti igieneCasa
 *       d) Cliccando sul quarto pulsante vengono visualizzati solo i prodotti igienePersona
 *       e) Cliccando sul quinto pulsante verrà eliminato l’ultimo elemento dell’ultimo array
 *       f) Cliccando sul sesto pulsante viene resettata la visualizzazione del testo predefinito
 */
let supermercato = [
    ["frutta", "verdura", "pane"],
    ["detersivo", "spugna", "sgrassatore"],
    ["shampoo", "balsamo", "dentifricio"]
]

const predefinito = "Benvenuto al supermercato."

function es10a() {
    document.getElementById("supermercato").innerHTML = supermercato.toString();
}

function es10b() {
    document.getElementById("supermercato").innerHTML = supermercato[0].toString();

}

function es10c() {
    document.getElementById("supermercato").innerHTML = supermercato[1].toString();

}

function es10d() {
    document.getElementById("supermercato").innerHTML = supermercato[2].toString();

}

function es10e() {
    let ultimoA = supermercato[supermercato.length-1];
    if (ultimoA != undefined) ultimoA.pop();
}

function es10f() {
    document.getElementById("supermercato").innerHTML = predefinito;
}

/*
 * Chiamo tutte le funzioni.
 */
es2();
es3();
es4();
es5();
es6();
es8();
es9();
es10f(); // chiamo il sesto pulsante per settare il testo predefinito.