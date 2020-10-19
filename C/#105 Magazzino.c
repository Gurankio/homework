/*
 * Jacopo Del Granchio
 * #105 ??.03.2020
 *
 * Articolo, Quantità, Prezzo.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <locale.h>
#include <stdarg.h>
#include <stdbool.h>
#include <string.h>

// Macro
#define chiedi(msg, format, ...) \
  printf(msg); \
  scanf(format, __VA_ARGS__);

// Costanti
#define NA 50
#define LS 32

// Prototipi
int menu();

void aggiungi(char articolo[NA][LS], int quantita[NA], float prezzo[NA], int *numeroArticoli);
void modifica(char articolo[NA][LS], int quantita[NA], float prezzo[NA], int numeroArticoli);
void rimuovi(char articolo[NA][LS], int quantita[NA], float prezzo[NA], int *numeroArticoli);
void stampa(char articolo[NA][LS], int quantita[NA], float prezzo[NA], int numeroArticoli);

void caricaDati(char articolo[NA][LS], int quantita[NA], float prezzo[NA], int *numeroArticoli);
void salvaDati(char articolo[NA][LS], int quantita[NA], float prezzo[NA], int numeroArticoli);

// Funzioni
int main() {
  setlocale(LC_ALL, "");

  char articolo[NA][LS];
  int quantita[NA];
  float prezzo[NA];
  int numeroArticoli = 0;

  caricaDati(articolo, quantita, prezzo, &numeroArticoli);

  int s;
  do {
    s = menu();
    switch (s) {
      case 1:
        if (numeroArticoli == NA) printf("Spazio esaurito.\n");
        else {
          aggiungi(articolo, quantita, prezzo, &numeroArticoli);
          salvaDati(articolo, quantita, prezzo, numeroArticoli);
        }
        break;

      case 2:
        modifica(articolo, quantita, prezzo, numeroArticoli);
        salvaDati(articolo, quantita, prezzo, numeroArticoli);
        break;

      case 3:
        if (numeroArticoli == 0) printf("Nessun dato.\n");
        else {
          rimuovi(articolo, quantita, prezzo, &numeroArticoli);
          salvaDati(articolo, quantita, prezzo, numeroArticoli);
        }
        break;

      case 4:
        stampa(articolo, quantita, prezzo, numeroArticoli);
        break;

      case 0:
        printf("Arrivedereci.\n");
        break;

      default:
        printf("Scelta non valida.\n");
    }
    printf("\n");
  } while (s != 0);

  return 0;
}

int menu() {
  printf("1) Aggiungi\n");
  printf("2) Modifica\n");
  printf("3) Rimuovi\n");
  printf("4) Stampa\n");
  printf("0) Esci\n");
  printf("Scelta: ");

  int s;
  scanf("%d", &s);
  return s;
}

void aggiungi(char articolo[NA][LS], int quantita[NA], float prezzo[NA], int *numeroArticoli) {
  printf("Inserire nome articolo: ");
  scanf("%s", articolo[*numeroArticoli]);

  printf("Inserire quantita articolo: ");
  scanf("%d", &quantita[*numeroArticoli]);

  printf("Inserire prezzo articolo: ");
  scanf("%f", &prezzo[*numeroArticoli]);

  (*numeroArticoli)++;
}

void modifica(char articolo[NA][LS], int quantita[NA], float prezzo[NA], int numeroArticoli) {
  char buffer[LS];
  printf("Inserire nome articolo: ");
  scanf("%s", buffer);

  int i;
  for (i = 0; i < NA; i++) {
    if (strcmp(buffer, articolo[i]) == 0) break;
  }

  if (i == NA) {
    printf("Articolo non trovato.\n");
    return;
  }

  int nuovaQuantita;
  float nuovoPrezzo;

  printf("Inserire nuovo nome articolo: ");
  scanf("%s", buffer);

  printf("Inserire nuovo quantita articolo: ");
  scanf("%d", &nuovaQuantita);

  printf("Inserire nuovo prezzo articolo: ");
  scanf("%f", &nuovoPrezzo);

  printf("%s -> %s\n", articolo[i], buffer);
  printf("%d -> %d\n", quantita[i], nuovaQuantita);
  printf("%f -> %f\n", prezzo[i], nuovoPrezzo);
  printf("Confermare i cambiamenti? (Y/N) ");

  char c;
  scanf("%c", &c);
  scanf("%c", &c);

  if (c == 'y' || c == 'Y') {
    strcpy(articolo[i], buffer);
    quantita[i] = numeroArticoli;
    prezzo[i] = nuovoPrezzo;
    printf("Cambiamenti salvati.\n");
    return;
  }

  if (c == 'n' || c == 'N') {
    printf("Cambiamenti scartati.\n");
    return;
  }

  printf("Annullo.\n");
}

void rimuovi(char articolo[NA][LS], int quantita[NA], float prezzo[NA], int *numeroArticoli) {
  char buffer[LS];
  printf("Inserire nome articolo: ");
  scanf("%s", buffer);

  int i;
  for (i = 0; i < NA; i++) {
    if (strcmp(buffer, articolo[i]) == 0) break;
  }

  if (i == NA) {
    printf("Articolo non trovato.\n");
    return;
  }

  for (int j=i; j<NA; j++) {
    strcpy(articolo[j], articolo[j+1]);
    quantita[j] = quantita[j+1];
    prezzo[j] = prezzo[j+1];
  }

  printf("Articolo rimosso.\n");
  (*numeroArticoli)--;
}

void stampa(char articolo[NA][LS], int quantita[NA], float prezzo[NA], int numeroArticoli) {
  printf("%-*s %7s %7s\n", 16, "Articolo", "Prezzo", "Articolo");
  for (int i=0; i<numeroArticoli; i++) {
    printf("%-*s %7d %7.2f\n", 16, articolo[i], quantita[i], prezzo[i]);
  }
}

void caricaDati(char articolo[NA][LS], int quantita[NA], float prezzo[NA], int *numeroArticoli) {
  FILE *file;

  // Creo se non esiste
  file = fopen("data/#105/magazzino.txt", "a");
  fclose(file);

  // Leggo fino ad EOF. Non controllo errori di scanf (return == 3) o di letture (ferror()).
  file = fopen("data/#105/magazzino.txt", "r");
  while (fscanf(file, "%f, %d, %[^\n]", &prezzo[(*numeroArticoli)], &quantita[(*numeroArticoli)], articolo[(*numeroArticoli)]) != EOF) (*numeroArticoli)++;
  fclose(file);
}

void salvaDati(char articolo[NA][LS], int quantita[NA], float prezzo[NA], int numeroArticoli) {
  FILE *file;

  // Sovrascrivo tutti i dati.
  file = fopen("data/#105/magazzino.txt", "w");
  for (int i=0; i<numeroArticoli; i++) fprintf(file, "%f, %d, %s\n", prezzo[i], quantita[i], articolo[i]);
  fclose(file);
}
