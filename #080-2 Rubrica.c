/*
 * Jacopo Del Granchio
 * #080 07.01.2020
 *
 * Rubrica.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <locale.h>
#include <stdarg.h>
#include <stdbool.h>
#include <string.h>

#define MSL 33 // Max String Length

// Struct

typedef struct {
  char nome[MSL];
  char cognome[MSL];
  char indirizzo[MSL];
  char telefono[MSL];
} elemento;

// Macro
#define chiedi(msg, format, ...) \
  printf(msg); \
  scanf(format, __VA_ARGS__);

// Prototipi
int menu();
void add(elemento *, int);
void delete(elemento *, int);
void print(elemento *, int);
void search(elemento *, int);
elemento * enlarge(elemento *, int, int);

// Funzioni
int main() {
  setlocale(LC_ALL, "");

  int counter = 0, size = 1;
  elemento *rubrica = malloc(size * sizeof(elemento));

  int s;
  do {
    s = menu();

    switch (s) {
      case 1:
        add(rubrica, counter);
        counter++;
        break;

      case 2:
        delete(rubrica, counter);
        counter--;
        break;

      case 3:
        print(rubrica, counter);
        break;

      case 4:
        search(rubrica, counter);
        break;

      case 5:
        printf("Arrivederci.\n");
        break;

      default:
        break;
    }

    if (counter >= size) {
      rubrica = enlarge(rubrica, size, 2);
      size *= 2;
    }
  } while (s != 5);

  free(rubrica);
  return 0;
}

int menu() {
  printf("1) Inserisci\n");
  printf("2) Rimuovi\n");
  printf("3) Stampa\n");
  printf("4) Ricerca\n");
  printf("5) Esci\n");
  int s;
  chiedi("Scelta: ", "%d", &s);
  return s;
}

void add(elemento *rubrica, int counter) {
  chiedi("Inserisci il nome: ", "%s", rubrica[counter].nome);
  chiedi("Inserisci il cognome: ", "%s", rubrica[counter].cognome);
  chiedi("Inserisci il indirizzo: ", "%s", rubrica[counter].indirizzo);
  chiedi("Inserisci il telefono: ", "%s", rubrica[counter].telefono);
}

void delete(elemento *rubrica, int counter) {
  int pos;

  do {
    chiedi("Inserisci la posizione: ", "%d", &pos);

    if (pos < 1) printf("La posizione deve essere maggiore di 1.\n");

    if (pos > counter) printf("La posizione deve essere minore o uguale a %d.\n", counter);
  } while (pos < 1 || pos > counter);

  // pos -= 1;

  for (int i = pos; i < counter; ++i)
    rubrica[i - 1] = rubrica[i];

  // strcpy(rubrica[pos].nome, "-");
  // strcpy(rubrica[pos].cognome, "-");
  // strcpy(rubrica[pos].indirizzo, "-");
  // strcpy(rubrica[pos].telefono, "-");
}

void print(elemento *rubrica, int counter) {
  for (int i = 0; i < counter; ++i)
    printf("%d:\t%s\t%s\t%s\t%s\n", i, rubrica[i].nome, rubrica[i].cognome, rubrica[i].indirizzo, rubrica[i].telefono);
}

void search(elemento *rubrica, int counter) {
  printf("1) Nome\n");
  printf("2) Cognome\n");
  printf("3) Indirizzo\n");
  printf("4) Telefono\n");

  int s;
  chiedi("Scelta: ", "%d", &s);
  char val[MSL];
  chiedi("Valore: ", "%s", val);

  for (int i = 0; i < counter; ++i) {
    elemento e = rubrica[i];
    switch (s) {
      case 1:

        if (strcmp(e.nome, val) == 0) printf("%d:\t%s\t%s\t%s\t%s\n", i, e.nome, e.cognome, e.indirizzo, e.telefono);

        break;

      case 2:

        if (strcmp(e.cognome, val) == 0) printf("%d:\t%s\t%s\t%s\t%s\n", i, e.nome, e.cognome, e.indirizzo, e.telefono);

        break;

      case 3:

        if (strcmp(e.indirizzo, val) == 0) printf("%d:\t%s\t%s\t%s\t%s\n", i, e.nome, e.cognome, e.indirizzo, e.telefono);

        break;

      case 4:

        if (strcmp(e.telefono, val) == 0) printf("%d:\t%s\t%s\t%s\t%s\n", i, e.nome, e.cognome, e.indirizzo, e.telefono);

        break;

      default: break;
    }
  }

  printf("Fine ricerca.\n");
}

elemento * enlarge(elemento *array, int size, int modifier) {
  elemento *output = malloc(size * modifier * sizeof(elemento));

  for (int i = 0; i < size; i++)
    output[i] = array[i];

  free(array);
  return output;
}
