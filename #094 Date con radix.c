/*
 * Jacopo Del Granchio
 * #094 15.02.2020
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <locale.h>
#include <stdarg.h>
#include <stdbool.h>

// Macro
#define chiedi(msg, format, ...) \
  printf(msg); \
  scanf(format, __VA_ARGS__);

// Constanti
#define LS 20

typedef struct {
  int anno;
  int mese;
  int giorno;
} data;

typedef struct {
  char nome[LS];
  data data;
} persona;

// Prototipi
int menu();

void aggiungiStudente(persona [50], int *);
void rimuoviStudente(persona [50], int *);
void stampaStudenti(persona [50], int);

void ordinaNome(persona [50], int);
void ordinaData(persona [50], int);

// Funzioni
int main() {
  setlocale(LC_ALL, "");

  persona classe[50];
  int counter = 0, s;
  do {
    s = menu();

    switch (s) {
      case 1:
        break;

      case 2:
        break;

      case 3:
        break;

      case 4:
        break;

      case 5:
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
  printf("1) Aggiungi studente\n");
  // printf("2) Rimuovi studente\n");
  printf("3) Stampa studenti\n");
  //printf("4) Ordina per nome\n");
  printf("5) Ordina per data (Radix)\n");
  printf("0) Esci\n");

  int s;
  char nl;

  scanf("%d", &s);
  scanf("%c", &nl);
  printf("\n");
  return s;
}

void aggiungiStudente(persona studenti[50], int *counter) {
  printf("Inserire il nome dello studente: ");
  gets(studenti[*counter].nome);

  printf("Inserire l'anno di nascita dello studente: ");
  scanf("%d", &studenti[*counter].data.anno);

  printf("Inserire il mese di nascita dello studente: ");
  scanf("%d", &studenti[*counter].data.mese);

  printf("Inserire il giorno di nascita dello studente: ");
  scanf("%d", &studenti[*counter].data.giorno);

  (*counter)++;
}

void rimuoviStudente(persona studenti[50], int *counter) {
}

void stampaStudenti(persona studenti[50], int counter) {
  for (int i = 0; i < counter; i++) {
    persona t = studenti[i];
    printf("%-*s%04d.%02d.%02d\n", LS, t.nome, t.data.anno, t.data.mese, t.data.giorno);
  }
}

void ordinaNome(persona studenti[50], int counter) {
}

int converti(data data) {
  return data.anno * 10000 + data.mese * 100 + data.giorno;
}

int cifra(int pos, int input) {
  return input % (int)(pow(10, pos)) / pow(10, pos - 1);
}

void ordinaData(persona studenti[50], int counter) {
  persona radix[10][counter];
  int radixCounter[10] = { 0 };

  for (int k = 0; k < 8; k = 8) {
    for (int i = 0; i < counter; i++) {
      int data = cifra(k, converti(studenti[i].data));
      radix[data][radixCounter[data]++] = studenti[i];
    }
  }
}
