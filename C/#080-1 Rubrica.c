/*
 * Jacopo Del Granchio
 * #080 07.01.2020
 *
 * Rubrica. No struct.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <locale.h>
#include <stdarg.h>
#include <stdbool.h>
#include <string.h>

#define MSL 33 // Max String Length

// Macro
#define chiedi(msg, format, ...) \
  printf(msg); \
  scanf(format, __VA_ARGS__);

// Prototipi
int menu();
void add(char **, char **, char **, char **, int);
void delete(char **, char **, char **, char **, int);
void print(char **, char **, char **, char **, int);
void search(char **, char **, char **, char **, int);
char ** enlarge(char **, int, int);

// Funzioni
int main() {
  setlocale(LC_ALL, "");

  int counter = 0, size = 1;
  char **nome = malloc(size * sizeof(char *));
  char **cognome = malloc(size * sizeof(char *));
  char **indirizzo = malloc(size * sizeof(char *));
  char **telefono = malloc(size * sizeof(char *));

  for (int i = 0; i < size; ++i) {
    nome[i] = malloc(MSL * sizeof(char));
    cognome[i] = malloc(MSL * sizeof(char));
    indirizzo[i] = malloc(MSL * sizeof(char));
    telefono[i] = malloc(MSL * sizeof(char));
  }

  int s;
  do {
    s = menu();

    switch (s) {
      case 1:
        add(nome, cognome, indirizzo, telefono, counter);
        counter++;
        break;

      case 2:
        delete(nome, cognome, indirizzo, telefono, counter);
        // non diminuisco counter
        break;

      case 3:
        print(nome, cognome, indirizzo, telefono, counter);
        break;

      case 4:
        search(nome, cognome, indirizzo, telefono, counter);
        break;

      case 5:
        printf("Arrivederci.\n");
        break;

      default:
        break;
    }

    if (counter >= size) {
      nome = enlarge(nome, size, 2);
      cognome = enlarge(cognome, size, 2);
      indirizzo = enlarge(indirizzo, size, 2);
      telefono = enlarge(telefono, size, 2);
      size *= 2;
    }
  } while (s != 5);

  free(nome);
  free(cognome);
  free(indirizzo);
  free(telefono);
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

void add(char **nome, char **cognome, char **indirizzo, char **telefono, int counter) {
  chiedi("Inserisci il nome: ", "%s", nome[counter]);
  chiedi("Inserisci il cognome: ", "%s", cognome[counter]);
  chiedi("Inserisci il indirizzo: ", "%s", indirizzo[counter]);
  chiedi("Inserisci il telefono: ", "%s", telefono[counter]);
}

void delete(char **nome, char **cognome, char **indirizzo, char **telefono, int counter) {
  int pos;

  do {
    chiedi("Inserisci la posizione: ", "%d", &pos);

    if (pos < 1) printf("La posizione deve essere maggiore di 1.\n");

    if (pos > counter) printf("La posizione deve essere minore o uguale a %d.\n", counter);
  } while (pos < 1 || pos > counter);

  pos -= 1;

  nome[pos] = "-";
  cognome[pos] = "-";
  indirizzo[pos] = "-";
  telefono[pos] = "-";
}

void print(char **nome, char **cognome, char **indirizzo, char **telefono, int counter) {
  for (int i = 0; i < counter; ++i)
    printf("%d:\t%s\t%s\t%s\t%s\n", i, nome[i], cognome[i], indirizzo[i], telefono[i]);
}

void search(char **nome, char **cognome, char **indirizzo, char **telefono, int counter) {
  printf("1) Nome\n");
  printf("2) Cognome\n");
  printf("3) Indirizzo\n");
  printf("4) Telefono\n");

  int s;
  chiedi("Scelta: ", "%d", &s);
  char *val;
  chiedi("Valore: ", "%s", val);

  for (int i = 0; i < counter; ++i) {
    switch (s) {
      case 1:

        if (strcmp(nome[i], val) == 0) printf("%d:\t%s\t%s\t%s\t%s\n", i, nome[i], cognome[i], indirizzo[i], telefono[i]);

        break;

      case 2:

        if (strcmp(cognome[i], val) == 0) printf("%d:\t%s\t%s\t%s\t%s\n", i, nome[i], cognome[i], indirizzo[i], telefono[i]);

        break;

      case 3:

        if (strcmp(indirizzo[i], val) == 0) printf("%d:\t%s\t%s\t%s\t%s\n", i, nome[i], cognome[i], indirizzo[i], telefono[i]);

        break;

      case 4:

        if (strcmp(telefono[i], val) == 0) printf("%d:\t%s\t%s\t%s\t%s\n", i, nome[i], cognome[i], indirizzo[i], telefono[i]);

        break;

      default: break;
    }
  }

  printf("Fine ricerca.\n");
}

char ** enlarge(char **array, int size, int modifier) {
  char **output = malloc(size * modifier * sizeof(char *));

  for (int i = 0; i < size * modifier; ++i)
    output[i] = malloc(MSL * sizeof(char));

  for (int i = 0; i < size; i++)
    strcpy(output[i], array[i]);

  free(array);
  return output;
}
