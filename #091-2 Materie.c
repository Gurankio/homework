/*
 * Jacopo Del Granchio
 * #000 GG.MM.YYYY
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

// Costanti
const int LS = 20;

const int NS = 50;
const int NM = 10;

// Macro
#define chiedi(msg, format, ...) \
  printf(msg); \
  scanf(format, __VA_ARGS__);

// Prototipi
int menu();

void aggiungiMateria(char [NS][NM][LS], int, int *);
void rimuoviMateria(char [NS][NM][LS], int, int *);

void aggiungiStudente(char [NS][NM][LS], int *, int);
void cercaStudente(char [NS][NM][LS], int, int);
void rimuoviStudente(char [NS][NM][LS], int *, int);

void voti(char [NS][NM][LS], int, int);
void media(char [NS][NM][LS], int, int);
void bilancio(char [NS][NM][LS], int, int);

void stampa(char [NS][NM][LS], int, int);
int token(char [LS], char [LS][LS]);
int len(char *);

// Funzioni
int main() {
  setlocale(LC_ALL, "");

  char dati[NS][NM][LS];
  int studenti = 0, materie = 0;

  int s;
  do {
    s = menu();

    switch (s) {
      case 1:
        aggiungiMateria(dati, studenti, &materie);
        break;

      case 2:
        rimuoviMateria(dati, studenti, &materie);
        break;

      case 3:
        aggiungiStudente(dati, &studenti, materie);
        break;

      case 4:
        cercaStudente(dati, studenti, materie);
        break;

      case 5:
        rimuoviStudente(dati, &studenti, materie);
        break;

      case 6:
        voti(dati, studenti, materie);
        break;

      case 7:
        media(dati, studenti, materie);
        break;

      case 8:
        bilancio(dati, studenti, materie);
        break;

      case 0:
        printf("Arrivenderci.");
        break;

      default:
        printf("Scelta non valida.");
    }
  } while (s != 0);

  // getchar();
  // system("pause");
  return 0;
}

int menu() {
  printf("\n");
  printf("1) Aggiungi Materia\n");
  printf("2) Rimuovi Materia\n");
  printf("3) Aggiungi Studenti\n");
  printf("4) Ricerca Studente\n");
  printf("5) Rimuovi Studenti\n");
  printf("6) Stampa Voti\n");
  printf("7) Stampa Media\n");
  printf("8) Stampa Bilancio Bocciati/Promossi\n");

  int s;
  char nl;
  chiedi("Scelta: ", "%d", &s);
  scanf("%c", &nl);
  printf("\n");
  return s;
}

//

void aggiungiMateria(char dati[NS][NM][LS], int studenti, int *materie) {
  char buffer[LS];

  //
  // Nuova materia. Inserimento ordinato.

  printf("Inserire il nome della materia: ");
  gets(buffer);

  int indice;

  for (indice = 0; indice < *materie; indice++) {
    if (strcmp(dati[0][indice], buffer) > 0) {
      for (int j = *materie; j > indice; j--)
        for (int k = 0; k < studenti; k++)
          strcpy(dati[k][j], dati[k][j - 1]);

      break;
    }
  }

  strcpy(dati[0][indice], buffer);
  (*materie)++;

  //
  // Aggiorno i dati degli studenti.

  if (studenti != 0) printf("Inserire i voti separati da uno spazio!\n");

  for (int i = 0; i < studenti; i++) {
    int ripeti, numeroToken;
    char tokens[LS][LS];
    do {
      ripeti = 0;

      // Input
      printf("Inserire i voti dello studente \"%s\":", dati[i + 1][0]);
      gets(buffer);

      // Token
      numeroToken = token(buffer, tokens);

      // Controllo di ogni token
      for (int n = 0; n < numeroToken; n++) {
        float voto;
        sscanf(tokens[n], "%f", &voto);

        if (voto < 0 || voto > 10) {
          ripeti = 1;
          printf("Il voto %.2f non è valido.\n", voto);
        }
      }
    } while (ripeti);

    strcpy(dati[i + 1][indice], buffer);
  }
}

void rimuoviMateria(char dati[NS][NM][LS], int studenti, int *materie) {
  char buffer[LS];

  printf("Inserire la materia da rimuovere: ");
  gets(buffer);

  //
  // Ricerca

  int indice = -1;

  for (int i = 0; i < *materie; i++) {
    if (strcmp(buffer, dati[0][i]) == 0) {
      indice = i;
      break;
    }
  }

  if (indice == -1) {
    printf("Materia \"%s\" non trovata.\n", buffer);
    return;
  }

  //
  // Pulisco

  for (int i = indice; i < *materie - 1; i++)
    for (int j = 0; j < studenti; j++)
      strcpy(dati[j][i], dati[j][i + 1]);

  (*materie)--;

  printf("Rimossa la materia \"%s\".\n", buffer);
}

void aggiungiStudente(char dati[NS][NM][LS], int *studenti, int materie) {
  char buffer[LS];

  //
  // Nuova materia. Inserimento ordinato.

  printf("Inserire il nome dello studente: ");
  gets(buffer);

  int indice;

  for (indice = 0; indice < *studenti; indice++) {
    if (strcmp(dati[indice][0], buffer) > 0) {
      for (int j = *studenti; j > indice; j--)
        for (int k = 0; k < materie; k++)
          strcpy(dati[j][k], dati[j - 1][k]);

      break;
    }
  }

  strcpy(dati[indice][0], buffer);
  (*studenti)++;

  //
  // Aggiorno i dati dello studente.

  if (materie != 0) printf("Inserire i voti separati da uno spazio!\n");

  for (int i = 0; i < materie; i++) {
    int ripeti, numeroToken;
    char tokens[LS][LS];
    do {
      ripeti = 0;

      // Input
      printf("Inserire i voti della materia \"%s\":", dati[0][i + 1]);
      gets(buffer);

      // Token
      numeroToken = token(buffer, tokens);

      // Controllo di ogni token
      for (int n = 0; n < numeroToken; n++) {
        float voto;
        sscanf(tokens[n], "%f", &voto);

        if (voto < 0 || voto > 10) {
          ripeti = 1;
          printf("Il voto %.2f non è valido.\n", voto);
        }
      }
    } while (ripeti);

    strcpy(dati[indice][i + 1], buffer);
  }
}

void cercaStudente(char dati[NS][NM][LS], int studenti, int materie) {
}

void rimuoviStudente(char dati[NS][NM][LS], int *studenti, int materie) {
}

void voti(char dati[NS][NM][LS], int studenti, int materie) {
  stampa(dati, studenti, materie);
}

void media(char dati[NS][NM][LS], int studenti, int materie) {
}

void bilancio(char dati[NS][NM][LS], int studenti, int materie) {
}

//

void stampa(char dati[NS][NM][LS], int studenti, int materie) {
  for (int i = 0; i < studenti + 1; i++) {
    for (int j = 0; j < materie + 1; j++)
      printf("%*s", LS, dati[i][j]);

    printf("\n");
  }
}

//

int token(char input[LS], char output[LS][LS]) {
  for (int i = 0; i < LS; i++)
    for (int j = 0; j < LS; j++)
      output[i][j] = '\0';

  int countOut = 0;

  for (int i = 0; i < len(input) + 1; i++) {
    int j;

    for (j = i; j < len(input) + 1; j++) {
      if (input[j] == ' ' || input[j] == '\0') {
        int a;

        if (j - i == 0) break;

        for (a = 0; a < (j - i); a++)
          output[countOut][a] = input[i + a];

        countOut++;
        break;
      }
    }

    i = j;
  }

  return countOut;
}

int len(char *input) {
  int i = 0;

  while (input[i] != '\0') i++;

  return i;
}
