/*
 * Jacopo Del Granchio
 * #091 GG.MM.YYYY
 *
 * Lorem ipsum dolor sit amet, consectetur adipisicing elit,
 * sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <locale.h>
#include <stdarg.h>
#include <stdbool.h>
#include <string.h>
#include <time.h>

#include "libraries/atr.c"

// Constanti
const int NS = 50; // Massimo Numero Studenti
const int NM = 2; // Numero Materie
const int NP = 3; // Numero Prove per Materia

// Constanti Grafiche
const int CS = 9; // Larghezza Cella
const int LS = 20; // Lunghezza Stringa
const int MLAR = (CS * (NP + 2) + 1); // Larghezza Materia

// Macro
#define chiedi(msg, format, ...) \
  printf(msg); \
  scanf(format, __VA_ARGS__);

// Prototipi
int menu();

void aggiungiStudente(char [NM][LS], char [][LS], float [][NM][NP], int *);
void rimuoviStudente(char [][LS], float [][NM][NP], int *);

void stampa(char [][LS], char [][LS], float [][NM][NP], int);
void griglia(atr_layer *, int);
void testo(atr_layer *, char [][LS], char [][LS], int);
void numeri(atr_layer *, float [][NM][NP], int, int, int);

void promozione(char [][LS], char [][LS], float [][NM][NP], int);

// Funzioni
int main() {
  setlocale(LC_ALL, "");
  vts_activateCommands();

  char materie[NM][LS];

  for (int i = 0; i < NM; i++) {
    printf("Inserire il nome della materia: ");
    fflush(stdin);
    gets(materie[i]);
  }

  char studenti[NS][LS];
  float dati[NS][NM][NP];
  int numeroStudenti = 0;

  int s;
  do {
    s = menu();

    switch (s) {
      case 1:

        if (numeroStudenti != NS) aggiungiStudente(materie, studenti, dati, &numeroStudenti);

        break;

      case 2:

        if (numeroStudenti != 0) rimuoviStudente(studenti, dati, &numeroStudenti);

        break;

      case 3:

        if (numeroStudenti != 0) stampa(materie, studenti, dati, numeroStudenti);

        break;

      case 4:

        if (numeroStudenti != 0) promozione(materie, studenti, dati, numeroStudenti);

        break;

      case 0:
        printf("Arrivederci.\n");
        break;

      default:
        vts_foregroundBrightRed();
        printf("Scelta non valida.\n");
        vts_foregroundDefault();
    }
  } while (s != 0);

  return 0;
}

int menu() {
  printf("\n");
  printf("1) Aggiungi studente\n");
  printf("2) Rimuovi studente\n");
  printf("3) Stampa tabella\n");
  printf("4) Promozione\n");
  printf("0) Esci\n");

  int d;
  chiedi("Scelta: ", "%d", &d);

  char c;
  scanf("%c", &c); // Rimuove '\n'
  printf("\n");
  return d;
}

void aggiungiStudente(char materie[NM][LS], char studenti[][LS], float dati[][NM][NP], int *numero) {
  char buffer[LS];

  printf("Inserire il nome dello studente: ");
  fflush(stdin);
  gets(buffer);

  int i;

  for (i = 0; i < *numero; i++) {
    if (strcmp(studenti[i], buffer) > 0) {
      for (int a = *numero; a > i; a--) {
        strcpy(studenti[a], studenti[a - 1]);

        for (int m = 0; m < NM; m++)
          for (int p = 0; p < NM; p++)
            dati[a][m][p] = dati [a - 1][m][p];
      }

      break;
    }
  }

  strcpy(studenti[i], buffer);

  for (int m = 0; m < NM; m++) {
    printf("Materia %s:\n", materie[m]);

    for (int p = 0; p < NP; p++) {
      do {
        printf("\tInserire il voto della prova %d: ", p + 1);
        scanf("%f", &dati[i][m][p]);

        if (dati[i][m][p] < 0 || dati[i][m][p] > 10) {
          vts_foregroundBrightRed();
          printf("Il voto deve essere compreso tra 0 e 10.\n");
          vts_foregroundDefault();
        }
      } while (dati[i][m][p] < 0 || dati[i][m][p] > 10);
    }
  }

  (*numero)++;
}

void rimuoviStudente(char studenti[][LS], float dati[][NM][NP], int *numero) {
  char buffer[LS];

  printf("Inserire il nome dello studente da rimuovere: ");
  gets(buffer);

  int index = -1;

  for (int i = 0; i < *numero; i++) {
    if (strcmp(buffer, studenti[i]) == 0) {
      index = i;
      break;
    }
  }

  if (index == -1) {
    printf("Studente \"%s\" non trovato.\n", buffer);
    return;
  }

  for (int i = index; i < *numero - 1; i++) {
    strcpy(studenti[i], studenti[i + 1]);

    for (int m = 0; m < NM; m++)
      for (int p = 0; p < NM; p++)
        dati[i][m][p] = dati [i + 1][m][p];
  }

  (*numero)--;

  printf("Rimosso lo studente \"%s\".\n", buffer);
}

void stampa(char materie[][LS], char studenti[][LS], float dati[][NM][NP], int numeroStudenti) {
  const int LAR = LS + 1 + MLAR * NM;  // Larghezza Totale
  const int ALT = 2 * (numeroStudenti + 3); // Altezza Totale

  // 0 -> Griglia
  // 1 -> Testo
  // 2 -> Dati
  atr_layer layers[3];

  atr_layerStart(&layers[0], LAR, ALT);
  atr_layerStart(&layers[1], LAR, ALT);
  atr_layerStart(&layers[2], LAR, ALT);

  atr_layerTypeSet(&layers[0], ATR_LINES);

  griglia(&layers[0], numeroStudenti);
  testo(&layers[1], materie, studenti, numeroStudenti);
  numeri(&layers[2], dati, 0, numeroStudenti, numeroStudenti);

  atr_printLayers(layers, 3);

  atr_layerEnd(&layers[0]);
  atr_layerEnd(&layers[1]);
  atr_layerEnd(&layers[2]);
}

void griglia(atr_layer *layer, int numeroStudenti) {
  const int LAR = LS + 1 + MLAR * NM;  // Larghezza Totale
  const int ALT = 2 * (numeroStudenti + 3); // Altezza Totale

  // Righe
  for (int i = 0; i < LAR; i++)
    atr_layerSet(layer, i, 2, 'q');

  for (int n = 1; n <= numeroStudenti + 1; n++)
    for (int i = 0; i < LAR; i++)
      atr_layerSet(layer, i, 2 + n * 2, 'q');

  // Colonne
  for (int i = 0; i < ALT; i++) atr_layerSet(layer, LS + 1, i, 'x');

  for (int n = 1; n <= NM; n++) {
    for (int i = 0; i < ALT; i++)
      atr_layerSet(layer, LS + 1 + n * MLAR, i, 'x');

    for (int t = 0; t < NP + 2; t++)
      for (int i = 1; i < ALT; i++)
        atr_layerSet(layer, LS + 1 + (n - 1) * MLAR + t * CS, i, 'x');
  }
}

void testo(atr_layer *layer, char materie[][LS], char studenti[][LS], int numeroStudenti) {
  for (int n = 0; n < NM; n++) {
    for (int i = 0; i < NP; i++)
      atr_layerSetFormat(layer, LS + 2 + n * MLAR + i * CS, 1, "%s %d", "Prova", i);

    atr_layerSetBuffer(layer, LS + 2 + n * MLAR + NP * CS, 1, "Somma");
    atr_layerSetBuffer(layer, LS + 2 + n * MLAR + (NP + 1) * CS, 1, "Media");
  }

  for (int n = 0; n < NM; n++)
    atr_layerSetBuffer(layer, LS + 2 + n * MLAR, 0, materie[n]);

  atr_layerSetBuffer(layer, 0, 0, "Studenti");

  atr_layerSetBuffer(layer, 0, numeroStudenti * 2 + 3, "Somma");
  atr_layerSetBuffer(layer, 0, numeroStudenti * 2 + 5, "Media");

  for (int n = 0; n < numeroStudenti; n++)
    atr_layerSetBuffer(layer, 0, 3 + n * 2, studenti[n]);
}

void numeri(atr_layer *layer, float dati[][NM][NP], int start, int end, int numeroStudenti) {
  for (int s = start; s < end; s++) {
    for (int n = 0; n < NM; n++) {
      float somma = 0;

      for (int i = 0; i < NP; i++) {
        float t = dati[s][n][i];
        somma += t;
        atr_layerSetFormat(layer, LS + 2 + n * MLAR + i * CS, 3 + s * 2, "%*.2f", CS, t);
      }

      atr_layerSetFormat(layer, LS + 2 + n * MLAR + NP * CS, 3 + s * 2, "%*.2f", CS, somma);
      atr_layerSetFormat(layer, LS + 2 + n * MLAR + (NP + 1) * CS, 3 + s * 2, "%*.2f", CS, somma / NP);
    }
  }

  for (int n = 0; n < NM; n++) {
    float generale = 0;

    for (int i = 0; i < NP; i++) {
      float somma = 0;

      for (int s = 0; s < numeroStudenti; s++)
        somma += dati[s][n][i];

      generale += somma / numeroStudenti;
      atr_layerSetFormat(layer, LS + 2 + n * MLAR + i * CS, 3 + end * 2, "%*.2f", CS, somma);
      atr_layerSetFormat(layer, LS + 2 + n * MLAR + i * CS, 3 + (end + 1) * 2, "%*.2f", CS, somma / numeroStudenti);
    }

    atr_layerSetFormat(layer, LS + 2 + n * MLAR + (NP + 1) * CS, 3 + (end + 1) * 2, "%*.2f", CS, generale / NP);
  }
}

void promozione(char materie[][LS], char studenti[][LS], float dati[][NM][NP], int numeroStudenti) {
  const int PERCENTUALE_INIZIALE = 50;

  srand((unsigned)time(NULL) + rand());

  int numeroPromossi = 0;

  for (int s = 0; s < numeroStudenti; s++) {
    int promosso = 1;
    int percentuale = PERCENTUALE_INIZIALE;
    int insufficienti = 0;

    for (int m = 0; m < NM; m++) {
      float media = 0;

      for (int p = 0; p < NP; p++)
        media += dati[s][m][p];

      media /= NP;

      if (media < 6.0) insufficienti++;
    }

    insufficienti = insufficienti > 1 ? insufficienti - 1 : 0;

    for (int i = 0; i < insufficienti; i++)
      if (rand() % 100 > percentuale) promosso = 0;
      else percentuale /= 2;

    numeroPromossi += promosso;
    printf("Lo studente \"%s\" e' %s.\n", studenti[s], promosso ? "promosso" : "bocciato");
  }

  printf("\nSono stati promossi %d studenti e sono stati bocciati %d studenti.\n", numeroPromossi, numeroStudenti - numeroPromossi);
}
