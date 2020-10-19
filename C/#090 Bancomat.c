/*
 * Jacopo Del Granchio
 * #090 01.02.2020
 *
 * Bancomat:
 * 20 di ogni banconota (50, 20, 10) e vanno aggiornate le quatità dopo ogni prelievo controllo se può darli.
 * Max 400 = 5 da 50, 5 da 20, 5 da 10.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <locale.h>
#include <stdarg.h>
#include <stdbool.h>

// Constanti
const int INIZIALE = 20; // Quantita' di banconote iniziali
const int MASSIMO_TRANSAZIONE = 5; // Massimo quantita' di una banconota per transazione
const int MIN = 50, MAX = MASSIMO_TRANSAZIONE * (50 + 20 + 10); // Constanti di prelievo minimo e massimo

// Macro
#define chiedi(msg, format, ...) \
  printf(msg); \
  scanf(format, __VA_ARGS__);

// Prototipi
int menu();
void prelievo(int [3]);
void combinazioni(int quantita, int b50, int b20, int b10, int out[][3], int *contatoreOut);

// Funzioni
int main() {
  setlocale(LC_ALL, "");

  int banconote[3] = { INIZIALE, INIZIALE, INIZIALE };
  int scelta;

  do {
    scelta = menu();

    switch (scelta) {
      case 1:
        prelievo(banconote);
        break;

      // Opzione nascosta di debug
      case 2:
        printf("\n");
        int somma = 0;
        somma += banconote[0] * 50;
        somma += banconote[1] * 20;
        somma += banconote[2] * 10;
        printf("Il totale e' %d\n", somma);
        printf("%02d / %02d / %02d\n", banconote[0], banconote[1], banconote[2]);
        break;

      case 0:
        printf("Arrivederci.\n");
        break;
    }
  } while (scelta != 0);
  return 0;
}

// Stampa il menu e ritorna la scelta
int menu() {
  printf("\n");
  printf("1: Prelievo\n");
  printf("0: Esci\n");

  int n;
  chiedi("Scelta: ", "%d", &n);
  return n;
}

void prelievo(int banconote[3]) {
  int q;

  chiedi("Inserire la quatita': ", "%d", &q);

  // Controllo dell'input
  if (q < MIN || q > MAX || q % 10 != 0) {
    printf("Quantita' non valida.\n");
    return;
  }

  // Calcolo le combinazioni con la ricorsione
  int out[100][3], contatoreA = 0, contatoreB = 0;
  combinazioni(q, 5, 5, 5, out, &contatoreA);

  // Banconote rimaste
  int b50 = banconote[0] > MASSIMO_TRANSAZIONE ? MASSIMO_TRANSAZIONE : banconote[0];
  int b20 = banconote[1] > MASSIMO_TRANSAZIONE ? MASSIMO_TRANSAZIONE : banconote[1];
  int b10 = banconote[2] > MASSIMO_TRANSAZIONE ? MASSIMO_TRANSAZIONE : banconote[2];

  // Controllo se le combinazioni calcolate sono possibili date le quantita' di banconote rimaste
  for (int i = 0; i < contatoreA; i++) {
    if (out[i][0] <= b50 && out[i][1] <= b20 && out[i][2] <= b10) {
      out[contatoreB][0] = out[i][0];
      out[contatoreB][1] = out[i][1];
      out[contatoreB][2] = out[i][2];
      contatoreB++;
    }
  }

  // Controllo se esiste almeno una possibilita'
  if (contatoreB == 0) {
    printf("Non e' possibile prelevare questa quantita'.\n");
    return;
  }

  // Stampa delle possibilita'
  printf("     50 / 20 / 10\n");

  for (int i = 0; i < contatoreB; i++)
    printf("%03d) %02d / %02d / %02d\n", i + 1, out[i][0], out[i][1], out[i][2]);

  printf("\n");

  // Scelta della combinazioni
  chiedi("Scelta della combinazione: ", "%d", &q);

  if (q >= 1 && q <= contatoreB) {
    banconote[0] -= out[q - 1][0];
    banconote[1] -= out[q - 1][1];
    banconote[2] -= out[q - 1][2];

    printf("Grazie per la transazione.\n");
  } else printf("Scelta non valida.\n");
}

// Calcola le combinazioni sfruttando la ricorsione
void combinazioni(int quantita, int b50, int b20, int b10, int out[][3], int *contatoreOut) {
  // Se la quantita da prelevare e' 0, la aggiungo alla matrice nella posizione *contatoreOut
  if (quantita == 0) {
    int esiste = 0;

    // Controllo se la combinazione e' gia stata aggiunta
    for (int i = 0; i < *contatoreOut; i++)
      if (out[i][0] == (5 - b50) && out[i][1] == (5 - b20) && out[i][2] == (5 - b10)) esiste = 1;

    // Se non esiste la aggiungo
    if (!esiste) {
      out[*contatoreOut][0] = 5 - b50;
      out[*contatoreOut][1] = 5 - b20;
      out[*contatoreOut][2] = 5 - b10;
      (*contatoreOut)++;
    }
  }

  // Richiamo la funzione se la quantita' e' abbastanza alta e ci sono banconote rimaste

  if (quantita >= 50 && b50 > 0) combinazioni(quantita - 50, b50 - 1, b20, b10, out, contatoreOut);

  if (quantita >= 20 && b20 > 0) combinazioni(quantita - 20, b50, b20 - 1, b10, out, contatoreOut);

  if (quantita >= 10 && b10 > 0) combinazioni(quantita - 10, b50, b20, b10 - 1, out, contatoreOut);
}
