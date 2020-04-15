/*
 * Jacopo Del Granchio
 * #107 24.03.2020
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <locale.h>
#include <stdarg.h>
#include <stdbool.h>
#include <string.h>
#include <ctype.h>

#include "libraries/menu.c"

// Macro
#define INPUT_PATH  "./data/#107/input.txt"
#define OUTPUT_PATH "./data/#107/output.txt"

#define NI          20
#define LS          32

typedef struct {
  int partenza;
  int arrivo;
  char destinazione[LS];
} itinerario;

// Prototipi
void aggiungiItinerario(void *[]);
void modificaItenerario(void *[]);
void rimuoviItinerario(void *[]);
void filtraItinerari(void *[]);
void stampaDati(void *[]);

void carica(itinerario[NI], int *);
void salva(itinerario[NI], int);

// Funzioni
int main() {
  setlocale(LC_ALL, "");

  int *n = malloc(sizeof(int));
  itinerario dati[NI];
  carica(dati, n);

  menuCollection menuCollection;

  if (!loadMenu("./data/#107/menu.md", &menuCollection)) return 0;

  bindFunction(getChoice(&menuCollection, 0, 0), aggiungiItinerario, dati, n);
  bindFunction(getChoice(&menuCollection, 0, 1), modificaItenerario, dati, n);
  bindFunction(getChoice(&menuCollection, 0, 2), rimuoviItinerario, dati, n);
  bindFunction(getChoice(&menuCollection, 0, 3), filtraItinerari, dati, n);
  bindFunction(getChoice(&menuCollection, 0, 4), stampaDati, dati, n);
  render(getMenu(&menuCollection, 0));
  freeMenu(&menuCollection);
  free(n);
  return 0;
}

void aggiungiItinerario(void *params[]) {
  itinerario *dati = params[0];
  int *n = params[1];
  char buffer[LS];

  printf("Inserire destinazione: ");
  fgets(buffer, LS, stdin);   // "equivalente" di fflush(stdin);
  fgets(buffer, LS, stdin);

  for (int i = 0; i < strlen(buffer); i++)
    if (buffer[i] == '\n' || buffer[i] == ' ') buffer[i] = '\0';

  buffer[0] = toupper(buffer[0]);

  int partenza, arrivo;
  printf("Inserire orario partenza: ");
  scanf("%d", &partenza);

  printf("Inserire orario arrivo: ");
  scanf("%d", &arrivo);

  int index = 0;

  while (index < *n && strcasecmp(buffer, dati[index].destinazione) > 0) index++;

  while (index < *n && strcasecmp(buffer, dati[index].destinazione) == 0 && partenza > dati[index].partenza) index++;

  while (index < *n && strcasecmp(buffer, dati[index].destinazione) == 0 && partenza == dati[index].partenza && arrivo > dati[index].arrivo) index++;

  if (index == NI) {
    fprintf(stderr, "Spazio esaurito.\n");
    return;
  }

  for (int j = *n; j > index; j--)
    dati[j] = dati[j - 1];

  strcpy(dati[index].destinazione, buffer);
  dati[index].partenza = partenza;
  dati[index].arrivo = arrivo;

  (*n)++;

  salva(dati, *n);
}

int cercaItinerario(itinerario dati[NI], int n) {
  char buffer[LS];

  printf("Inserire destinazione: ");
  fgets(buffer, LS, stdin);   // "equivalente" di fflush(stdin);
  fgets(buffer, LS, stdin);

  for (int i = 0; i < strlen(buffer); i++)
    if (buffer[i] == '\n' || buffer[i] == ' ') buffer[i] = '\0';

  buffer[0] = toupper(buffer[0]);

  int found = 0, foundIndex[NI];

  for (int i = 0; i < n; i++) {
    if (strcmp(buffer, dati[i].destinazione) == 0) {
      foundIndex[found] = i;
      found++;
    }
  }

  if (found == 0) return -1;

  printf("Ci sono %d pullman per la destinazione inserita:\n", found);

  for (int i = 0; i < found; i++)
    printf("%d) %-*s %d -> %d\n", i + 1, LS, dati[foundIndex[i]].destinazione, dati[foundIndex[i]].partenza, dati[foundIndex[i]].arrivo);

  int s;
  do {
    printf("Scelta: ");
    scanf("%d", &s);

    if (s < 1 || s > found) printf("Scelta non valida.\n");
  } while (s < 1 || s > found);
  return foundIndex[s - 1];
}

void modificaItenerario(void *params[]) {
  itinerario *dati = params[0];
  int n = *(int *)params[1];

  int index = cercaItinerario(dati, n);

  if (index == -1) {
    printf("Itinerario non trovata.\n");
    return;
  }

  char buffer[LS];
  printf("Inserire destinazione: ");
  fgets(buffer, LS, stdin);   // "equivalente" di fflush(stdin);
  fgets(buffer, LS, stdin);

  for (int i = 0; i < strlen(buffer); i++)
    if (buffer[i] == '\n' || buffer[i] == ' ') buffer[i] = '\0';

  buffer[0] = toupper(buffer[0]);

  int partenza, arrivo;
  printf("Inserire orario partenza: ");
  scanf("%d", &partenza);

  printf("Inserire orario arrivo: ");
  scanf("%d", &arrivo);

  char s;
  printf("Confermo modifica? (S/N) ");
  scanf("%c", &s);
  scanf("%c", &s);

  if (s == 'S' || s == 's') {
    strcpy(dati[index].destinazione, buffer);
    dati[index].partenza = partenza;
    dati[index].arrivo = arrivo;
    salva(dati, n);
    printf("Modiche salvate.\n");
  } else printf("Modifiche annullate.\n");
}

void rimuoviItinerario(void *params[]) {
  itinerario *dati = params[0];
  int *n = (int *)params[1];

  int index = cercaItinerario(dati, *n);

  if (index == -1) {
    printf("Itinerario non trovata.\n");
    return;
  }

  for (int j = index; j < *n; j++)
    dati[j] = dati[j + 1];

  (*n)--;

  salva(dati, *n);
  printf("Dstinazione rimossa.\n");
}

void filtraItinerari(void *params[]) {
  itinerario *dati = params[0];
  int n = *(int *)params[1];
  char buffer[LS];

  printf("Inserire destinazione: ");
  fgets(buffer, LS, stdin);   // "equivalente" di fflush(stdin);
  fgets(buffer, LS, stdin);

  for (int i = 0; i < strlen(buffer); i++)
    if (buffer[i] == '\n' || buffer[i] == ' ') buffer[i] = '\0';

  buffer[0] = toupper(buffer[0]);

  int arrivo;
  printf("Inserire orario di arrivo massimo: ");
  scanf("%d", &arrivo);

  int found = 0, foundIndex[NI];

  for (int i = 0; i < n; i++) {
    if (strcmp(buffer, dati[i].destinazione) == 0 && dati[i].arrivo <= arrivo) {
      foundIndex[found] = i;
      found++;
    }
  }

  if (found == 0) {
    printf("Non ci sono pullman per i parametri inseriti.\n");
    return;
  }

  printf("%s %d pullman per i parametri inseriti:\n", found == 1 ? "C'e'" : "Ci sono", found);

  for (int i = 0; i < found; i++)
    printf("%-*s %d -> %d\n", LS, dati[foundIndex[i]].destinazione, dati[foundIndex[i]].partenza, dati[foundIndex[i]].arrivo);
}

void stampaDati(void *params[]) {
  itinerario *dati = params[0];
  int n = *(int *)params[1];

  printf("%-*s Partenza -> Arrivo\n", LS - 6, "Destinazione");

  for (int i = 0; i < n; i++)
    printf("%-*s %d -> %d\n", LS, dati[i].destinazione, dati[i].partenza, dati[i].arrivo);

  char s;
  printf("\nSalvo la stampa? (S/N) ");
  scanf("%c", &s);
  scanf("%c", &s);

  if (s == 'S' || s == 's') {
    FILE *file = fopen(OUTPUT_PATH, "w");

    if (file == NULL) {
      printf("Errore di I/O.\n");
      return;
    }

    fprintf(file, "%-*s Partenza -> Arrivo\n", LS - 6, "Destinazione");

    for (int i = 0; i < n; i++)
      fprintf(file, "%-*s %d -> %d\n", LS, dati[i].destinazione, dati[i].partenza, dati[i].arrivo);

    fclose(file);
    printf("Stampa salvata.\n");
  } else printf("Stampa non salvata.\n");
}

void carica(itinerario dati[NI], int *n) {
  FILE *file = fopen(INPUT_PATH, "a");

  fclose(file);
  file = fopen(INPUT_PATH, "r");

  if (file == NULL) {
    printf("Errore di I/O.\n");
    return;
  }

  (*n) = 0;
  char buffer[LS * 2];

  while (fgets(buffer, LS * 2, file) != NULL) {
    sscanf(buffer, "%s %d %d", dati[(*n)].destinazione, &dati[(*n)].partenza, &dati[(*n)].arrivo);
    (*n)++;
  }

  fclose(file);
}

void salva(itinerario dati[NI], int n) {
  FILE *file = fopen(INPUT_PATH, "w");

  for (int i = 0; i < n; i++)
    fprintf(file, "%s %d %d\n", dati[i].destinazione, dati[i].partenza, dati[i].arrivo);

  fclose(file);
}
