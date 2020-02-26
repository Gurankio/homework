/*
 * Jacopo Del Granchio
 * #100 26.02.2020
 *
 * Vettori paralleli e Stringhe
 * Gestione squadre di calcio
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MaxN 100

void inizializzaSquadre(char[][30], char[], int[], int );
void visualizzaSerie(char[][30], char[], int[], char ,int);
void informazioniSquadra(char[][30], char[], int[], int );
void campione(char[][30], char[], int[], char ,int );
void visualizzaClassifica (char V[][30], char [], int [], char, int);
void ordina (char[][30], int[], int );
void ordinaNome (char[][30], char[], int[], char, int);

int main() {

  char nomeS[MaxN][30];	//vettore di stringhe con i momi delle squadre
  char serie[MaxN];		//cettore di char che contiene la serie dove gioca la squadra
  int punti[MaxN];		//vettore di interi col punteggio della sqradra

  char s;
  int k;

  int N=6;

  //printf ("\nInserisci il numero totale di squadre serie A + serie B:\n");
  //scanf ("%d",&N);

  inizializzaSquadre(nomeS, serie, punti, N);

  do {
    printf("\n");
	  printf ("Inserisci 1 per elenco squadre serie A:\n");
	  printf ("Inserisci 2 per elenco squadre serie B:\n");
	  printf ("Inserisci 3 per informazioni squadra :\n");
	  printf ("Inserisci 4 per classifica squadre serie richiesta:\n");
	  printf ("Inserisci 5 per ricerca squadra campione serie richiesta:\n");
	  printf ("Inserisci 6 per ordinare le squadre della serie richiesta in ordine alfabetico:\n");
	  printf ("Inserisci 0 per terminare:\n");
	  printf ("Inserisci la tua scelta :\n");
	  scanf ("%d",&k);
	  fflush(stdin);

	  switch (k) {
		  case 1:
			  visualizzaSerie(nomeS, serie ,punti, 'A', N);
	    	break;

	    case 2:
        visualizzaSerie(nomeS, serie ,punti, 'B', N);
	    	break;

	    case 3:
        informazioniSquadra(nomeS, serie ,punti,N);
	    	break;

	    case 4:
			  printf ("Inserisci la serie che vuoi visualizzare :\n");
			  scanf("%c",&s);
			  visualizzaClassifica (nomeS, serie ,punti, s,N);
	    	break;
	    case 5:
	    	printf ("Inserisci la serie che vuoi visualizzare :\n");
			  scanf("%c",&s);
			  campione(nomeS, serie ,punti, s, N);
	    	break;
	    case 6:
			  printf ("Inserisci la serie che vuoi visualizzare :\n");
			  scanf("%c",&s);
			  ordinaNome(nomeS,serie,punti,s,N);
	      break;

	    case 0:
        printf("Arrivederci.\n");
	    	break;

	    default:
	    	printf("Scelte consentite 1,2,3,4,6 o 0 :\n");
	}

	} while(k != 0);

  return 0;
}


void visualizzaSerie (char V[][30], char S[], int P[], char cat, int n){
	//visualizza le squadre della serie  specificata dal parametro s

  for (int i=0; i<n; i++) {
    if (S[i] == cat) {
      printf("%-*s -> %d\n", 30, V[i], P[i]);
    }
  }
}

void visualizzaClassifica (char V[][30], char S[],int P[], char cat , int n){
	//Crea un nuovo vettore che conterr� solo le squadre della  serie richiesta, lo ordina e visualizza la classifica.
  char sottoSquadre[n][30];
  int conta = 0, punti[n];

  for (int i=0; i<n; i++) {
    if (S[i] == cat) {
      strcpy(sottoSquadre[conta], V[i]);
      punti[conta] = P[i];
      conta++;
    }
  }

  ordina(sottoSquadre, punti, conta);

  for (int i=0; i<conta; i++) {
    printf("%d) %-*s -> %d\n", i, 30, sottoSquadre[i], punti[i]);
  }
}

void ordina (char V [][30], int punti[], int n){
	// effettua l'ordinamento delle squadre in base al punteggio decrescente
  for (int i=0; i<n-1; i++) {
    for (int j=i; j<n; j++) {
      if (punti[i] < punti[j]) {
        char buffer[30];
        strcpy(buffer, V[i]);
        strcpy(V[i], V[j]);
        strcpy(V[j], buffer);

        int tempP = punti[i];
        punti[i] = punti[j];
        punti[j] = tempP;
      }
    }
  }
}

void ordinaNome(char V [][30], char serie[], int punti[], char s, int n){
	// effettua l'ordinamento delle squadre della serie S in ordine alfabetico
  for (int i=0; i<n-1; i++) {
    if (serie[i] != s) {
      continue;
    }

    for (int j=i; j<n; j++) {
      if (serie[j] != s) {
        continue;
      }

      if (strcmp(V[i], V[j]) > 0) {
        char buffer[30];
        strcpy(buffer, V[i]);
        strcpy(V[i], V[j]);
        strcpy(V[j], buffer);

        char tempS = serie[i];
        serie[i] = serie[j];
        serie[j] = tempS;

        int tempP = punti[i];
        punti[i] = punti[j];
        punti[j] = tempP;
      }
    }
  }

  visualizzaSerie(V, serie, punti, s, n);
}

void informazioniSquadra(char V[][30], char S[], int P[], int n ){
	// dato il nome di una squadra, se compare tra quelle inserite nell'elenco ne stampa
	// la serie e i punti, altrimenti da un messaggio di errore
  char buffer[30];

  printf("Inserire nome squadra: ");
  fflush(stdin);
  gets(buffer);

  int i;
  for (i=0; i<n; i++) {
    if (strcmp(buffer, V[i]) == 0) {
      break;
    }
  }

  if (i == n) {
    printf("Squadra non trovata.\n");
  } else {
    printf("Punti: %d\n", P[i]);
    printf("Serie: %d\n", S[i]);
  }
}

void campione(char V[][30], char S[], int P[], char s, int n ){
	// ricerca tra le squadre della serie richiesta quella con punteggio massimo
	// senza creare nessun vettore di appoggio (SENZA NESSUNA COPIA, ne dei nomi delle squadre ne dei punteggi)

  int camp = 0, campI = 0;
  int i;

  for (i=0; i<n; i++) {
    if (S[i] == s) {
      camp = P[i];
      campI = i;
    }
  }

  for (; i<n; i++) {
    if (S[i] == s) {
      if (P[i] > camp) {
        camp = P[i];
        campI = i;
      }
    }
  }

  printf("Il Campione e' %s\n", V[campI]);
}

void inizializzaSquadre(char V[6][30], char S[], int P[], int N){
	// inizializza i vettori nomesquadre, serie e punti e  N
	strcpy(V[0],"Fiore");
	strcpy(V[1],"Juve");
	strcpy(V[2],"Napule");
	strcpy(V[3],"Bari");
	strcpy(V[4],"Pescia");
	strcpy(V[5],"Livorno");

	S[0]='A';
	S[1]='B';
	S[2]='A';
	S[3]='A';
	S[4]='B';
	S[5]='B';

	P[0]=12;
	P[1]=2;
	P[2]=3;
	P[3]=4;
	P[4]=31;
	P[5]=3;
}
