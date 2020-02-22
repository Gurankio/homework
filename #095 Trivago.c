/*
 * Jacopo Del Granchio
 * #095 18.02.2020
 *
 * Hotel? Trivago.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <locale.h>
#include <stdarg.h>
#include <stdbool.h>
#include <string.h>

#include "libraries/vts.c"

#define MA 50
#define LS 20
#define MS 10 // Massimi Servizi

// Macro
#define chiedi(msg, format, ...) \
  printf(msg); \
  scanf(format, __VA_ARGS__);

// Prototipi
int menu();

void aggiungiAlbergo(char[MA][LS], int[MA], char[MA][MS][LS], int *);
void ricercaAlbergo(char[MA][LS], int[MA], char[MA][MS][LS], int);
void stampaAlberghi(char[MA][LS], int[MA], char[MA][MS][LS], int);
void stampaOrdinato(char[MA][LS], int[MA], char[MA][MS][LS], int);

// Funzioni
int main() {
  setlocale(LC_ALL, "");

  char nomi[MA][LS];
  int stelle[MA];
  char servizi[MA][MS][LS];

  for (int i = 0; i < MA; i++)
    for (int j = 0; j < MS; j++)
      strcpy(servizi[i][j], "");

  int numeroAlberghi = 0;

  int s;
  do {
    s = menu();

    switch (s) {
      case 1:

        if (numeroAlberghi != MA) aggiungiAlbergo(nomi, stelle, servizi, &numeroAlberghi);
        else printf("Massimi alberghi.");

        break;

      case 2:
        ricercaAlbergo(nomi, stelle, servizi, numeroAlberghi);
        break;

      case 3:
        stampaAlberghi(nomi, stelle, servizi, numeroAlberghi);
        break;

      case 4:
        stampaOrdinato(nomi, stelle, servizi, numeroAlberghi);
        break;

      case 0:
        printf("Arrivederci.\n");
        break;

      default:
        printf("Scelta non valida.\n");
    }
  } while (s != 0);


  // getchar();
  // system("pause");
  return 0;
}

int menu() {
  printf("1) Aggiungi albergo\n");
  printf("2) Ricerca albergo\n");
  printf("3) Stampa alberghi\n");
  printf("4) Stampa alberghi per stelle\n");
  printf("0) Esci\n");
  printf("Scelta: ");

  int s;
  scanf("%d", &s);
  return s;
}

void aggiungiAlbergo(char nomi[MA][LS], int stelle[MA], char servizi[MA][MS][LS], int *numeroAlberghi) {
  printf("Inserire il nome dell'albergo: ");
  scanf("%s", nomi[*numeroAlberghi]);

  int t;
  do {
    printf("Inserire le stelle dell'albergo: ");
    scanf("%d", &t);
  } while (t < 0 || t > 5);
  stelle[*numeroAlberghi] = t;

  int n;
  printf("Inserire di quanti servizi dispone l'albergo: ");
  scanf("%d", &n);

  for (int i = 0; i < n; i++) {
    printf("Inserire il %d servizio: ", i + 1);
    scanf("%s", servizi[*numeroAlberghi][i]);
  }

  (*numeroAlberghi)++;
}

void ricercaAlbergo(char nomi[MA][LS], int stelle[MA], char servizi[MA][MS][LS], int numeroAlberghi) {
  int stelleRichieste;

  do {
    printf("Inserire le stelle dell'albergo: ");
    scanf("%d", &stelleRichieste);
  } while (stelleRichieste < 0 || stelleRichieste > 5);

  int controllaServizi = 1;
  char buffer[LS];
  printf("Inserire il servizio richiesto: ");
  scanf("%s", buffer);

  if (strcmp(buffer, "-") == 0) controllaServizi = 0;

  int numeroValidi = 0, validi[numeroAlberghi];

  for (int i = 0; i < numeroAlberghi; i++) {
    if (stelle[i] < stelleRichieste) continue;

    for (int j = 0; j < MS; j++) {
      if (controllaServizi) {
        if (strcmp(servizi[i][j], "") == 0) continue;

        if (strcmp(buffer, servizi[i][j]) != 0) continue;
      }

      validi[numeroValidi++] = i;
      break;
    }
  }

  if (numeroValidi == 0) {
    printf("Nessun risultato.\n");
    return;
  }

  printf("Alberghi validi:\n");

  for (int i = 0; i < numeroValidi; i++)
    printf("\t%s\n", nomi[validi[i]]);
}

void stampaAlberghi(char nomi[MA][LS], int stelle[MA], char servizi[MA][MS][LS], int numeroAlberghi) {
  if (numeroAlberghi == 0) {
    printf("Non ci sono alberghi.\n");
    return;
  }

  printf("%-*s%-*s     %-*s\n", LS, "Nome", LS / 2, "Stelle", LS, "Servizi");

  for (int i = 0; i < numeroAlberghi; i++) {
    printf("%-*s%d%*c     ", LS, nomi[i], stelle[i], (LS / 2) - 1, ' ');
    char temp[LS + 3], buffer[MS * (LS + 3)] = "";

    for (int j = 0; j < MS; j++) {
      if (strcmp(servizi[i][j], "") != 0) {
        sprintf(temp, "%s | ", servizi[i][j]);
        strcat(buffer, temp);
      }
    }

    if (strcmp(buffer, "") == 0) strcpy(buffer, "-");
    else buffer[strlen(buffer) - 2] = ' ';

    printf("%s\n", buffer);
  }
}

void stampaOrdinato(char nomi[MA][LS], int stelle[MA], char servizi[MA][MS][LS], int numeroAlberghi) {
  if (numeroAlberghi == 0) {
    printf("Non ci sono alberghi.\n");
    return;
  }

  int c = 0, ordinato[numeroAlberghi];

  for (int i = 5; i > 0; i--)
    for (int j = 0; j < numeroAlberghi; j++)
      if (stelle[j] == i) ordinato[c++] = j;

  printf("%-*s%-*s     %-*s\n", LS, "Nome", LS / 2, "Stelle", LS, "Servizi");

  for (int i = 0; i < numeroAlberghi; i++) {
    printf("%-*s%d%*c     ", LS, nomi[ordinato[i]], stelle[ordinato[i]], (LS / 2) - 1, ' ');
    char temp[LS + 3], buffer[MS * (LS + 3)] = "";

    for (int j = 0; j < MS; j++) {
      if (strcmp(servizi[ordinato[i]][j], "") != 0) {
        sprintf(temp, "%s | ", servizi[ordinato[i]][j]);
        strcat(buffer, temp);
      }
    }

    if (strcmp(buffer, "") == 0) strcpy(buffer, "-");
    else buffer[strlen(buffer) - 2] = ' ';

    printf("%s\n", buffer);
  }
}
