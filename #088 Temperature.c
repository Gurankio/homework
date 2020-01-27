/*
 * Jacopo Del Granchio
 * #088 22.01.2020
 *
 * Lorem ipsomma dolor sit amet, consectetur adipisicing elit,
 * sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <locale.h>
#include <stdarg.h>
#include <stdbool.h>
#include <time.h>

#include "libraries/vts.c" // Colori per il terminale e altro.
#include "libraries/sleep.c" // Sleep multi-piattaforma.

// Costanti
#define NC 4000  // Numero di città (Non piu di 11.)
#define LS 64 /// Lunghezza massima di una stringa
#define LC 16 // larghezza Cella

const char STAGIONI[4][LS] = { "Inverno", "Primavera", "Estate", "Autunno" };

// Prototipi
void carica(char nomi[NC][LS], float temperature[NC][4]);
void caricaRand(char nomi[NC][LS], float temperature[NC][4]);

void griglia(int colonne, int righe, int larghezzaCella);
void stampaCoord(const char stringa[LS], int x, int y, int larghezzaCella);
void stampa(char nomi[NC][LS], float temperature[NC][4], int estremi[NC][4], float medie[NC + 1]);

void calcolaMedie(float temperature[NC][4], float medie[NC + 1]);
void calcolaEstremi(float temperature[NC][4], int estremi[NC][4]);

// Macro
#define stampaCoord(stringa, x, y, larghezzaCella) stampaCoordSide(stringa, x, y, larghezzaCella, false)

// Funzioni
int main() {
  setlocale(LC_ALL, "");
  vts_activateCommands();

  char nomi[NC][LS] = { "" };
  float temperature[NC][4];

  //carica(nomi, temperature);
  caricaRand(nomi, temperature);

  float medie[NC + 1];
  calcolaMedie(temperature, medie);

  int estremi[NC][4];
  calcolaEstremi(temperature, estremi);


  stampa(nomi, temperature, estremi, medie);

  // Sposto il cursore fuori dalla griglia.
  vts_xy(0, NC * 2 + 3);
  return 0;
}

// Input

void carica(char nomi[NC][LS], float temperature[NC][4]) {
  for (int i = 0; i < NC; i++) {
    fflush(stdin);
    printf("Inserire il nome della città: ");
    gets(nomi[i]);

    for (int j = 0; j < 4; j++) {
      printf("Inserire la temperatura in %s: ", STAGIONI[j]);
      scanf("%f", &temperature[i][j]);
    }

    printf("\n");
  }
}

void caricaRand(char nomi[NC][LS], float temperature[NC][4]) {
  srand((unsigned)time(NULL) + rand());

  for (int i = 0; i < NC; i++) {
    nomi[i][0] = 65 + i;
    nomi[i][1] = '\0';

    for (int j = 0; j < 4; j++)
      temperature[i][j] = (float)(rand() % 4000 - 10) / 100;
  }
}

// Output

void griglia(int colonne, int righe, int larghezzaCella) {
  cp_sleep(250);
  vts_resize((larghezzaCella + 1) * colonne, righe * 2 + 2);
  vts_lineDrawingSet();

  for (int j = 0; j < colonne - 1; j++)
    for (int i = 0; i < righe * 2 - 1; i++) {
      vts_xy((larghezzaCella + 1) * (j + 1), i);
      printf("x");
    }

  for (int j = 0; j < righe - 1; j++)
    for (int i = 0; i < (larghezzaCella + 1) * colonne; i++) {
      vts_xy(i, j * 2 + 1);
      printf("%c", i != 0 && i % (larghezzaCella + 1) == 0 ? 'n' : 'q');
    }

  vts_asciiSet();
  vts_xy(0, righe * 2 + 2);
}

void stampaCoordSide(const char stringa[LS], int x, int y, int larghezzaCella, bool right) {
  vts_xy((larghezzaCella + 1) * (x) + (x != 0), y * 2);

  if (right) printf("%*s\n", larghezzaCella, stringa);
  else printf("%-*s\n", larghezzaCella, stringa);
}

/*
 *  1: Rosso
 *  0: Neutra
 * -1: Blu
 */
void stampa(char nomi[NC][LS], float temperature[NC][4], int estremi[NC][4], float medie[NC + 1]) {
  griglia(6, NC + 1, LC);

  stampaCoord("Citta'", 0, 0, LC);

  for (int i = 0; i < 4; i++)
    stampaCoord(STAGIONI[i], i + 1, 0, LC);

  stampaCoord("Medie", 5, 0, LC);

  char buffer[LS];

  for (int i = 0; i < NC; i++) {
    stampaCoord(nomi[i], 0, i + 1, LC);

    for (int j = 0; j < 4; j++) {
      switch (estremi[i][j]) {
        case 1:
          vts_foregroundBrightRed();
          break;

        case -1:
          vts_foregroundBrightCyan();
          break;

        default:
          vts_foregroundDefault();
      }

      sprintf(buffer, "%.2f", temperature[i][j]);
      stampaCoordSide(buffer, j + 1, i + 1, LC, true);
    }

    vts_foregroundDefault();
    sprintf(buffer, "%.2f", medie[i]);
    stampaCoordSide(buffer, 5,  i + 1, LC, true);
  }

  stampaCoord("Generale", 0, NC + 1, LC);
  sprintf(buffer, "%.2f", medie[NC]);
  stampaCoordSide(buffer, 5, NC + 1, LC, true);
}

// Logica

void calcolaMedie(float temperature[NC][4], float medie[NC + 1]) {
  float sommaTotale = 0.0;

  for (int i = 0; i < NC; i++) {
    float somma = 0.0;

    for (int j = 0; j < 4; j++)
      somma += temperature[i][j];

    sommaTotale += somma;
    medie[i] = somma / 4;
  }

  medie[NC] = sommaTotale / (NC * 4);
}

void calcolaEstremi(float temperature[NC][4], int estremi[NC][4]) {
  float min = temperature[0][0], max = temperature[0][0];

  for (int i = 0; i < NC; i++)
    for (int j = 0; j < 4; j++) {
      if (temperature[i][j] < min) min = temperature[i][j];

      if (temperature[i][j] > max) max = temperature[i][j];
    }

  for (int i = 0; i < NC; i++)
    for (int j = 0; j < 4; j++) {
      estremi[i][j] = 0;

      if (temperature[i][j] == min) estremi[i][j] = -1;

      if (temperature[i][j] == max) estremi[i][j] = 1;
    }
}
