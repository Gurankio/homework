/*
 * Jacopo Del Granchio
 * #106 13.03.2020
 *
 * Nome, Localita, Coperti, Stelle, Menu (Nome, Costo, Glutine).
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
#define LS 32
#define NR 10
#define NP 100

typedef struct {
  char nome[LS];
  float costo;
  int glutine;
} portata;

typedef struct {
  char nome[LS];
  char localita[LS];
  int coperti;
  int stelle;
  portata menu[NP];
  int numeroPortate;
} ristorante;

// Prototipi
int menu();

void stampaRistorante(ristorante);
void aggiungiPortata(portata [NP], int*);
void eliminaPortata(portata [NP], int*);
int ricercaPortata(portata [NP], int);
void ordinaPortate(portata [NP], int);
void stampaMenuGlutine(portata [NP], int, int);

void caricaDati(ristorante[NR], int);
void salvaDati(ristorante[NR], int);

// Funzioni
int main() {
  setlocale(LC_ALL, "");

  ristorante ristoranti[NR];
  caricaDati(ristoranti, 0);

  int s;
  do {
    s = menu();

    printf("\n");
    switch (s) {
      case 1:
        stampaRistorante(ristoranti[0]);
        break;

      case 2:
        aggiungiPortata(ristoranti[0].menu, &ristoranti[0].numeroPortate);
        salvaDati(ristoranti, 0);
        break;

      case 3:
        eliminaPortata(ristoranti[0].menu, &ristoranti[0].numeroPortate);
        salvaDati(ristoranti, 0);
        break;

      case 4:
        ricercaPortata(ristoranti[0].menu, ristoranti[0].numeroPortate);
        break;

      case 5:
        ordinaPortate(ristoranti[0].menu, ristoranti[0].numeroPortate);
        salvaDati(ristoranti, 0);
        break;

      case 6:
        stampaMenuGlutine(ristoranti[0].menu, ristoranti[0].numeroPortate, -1);
        break;

      case 7:
        {
          char c;
          printf("Glutine? (Y/N) ");
          scanf(" %c", &c);

          if (c == 'n' || c == 'N') stampaMenuGlutine(ristoranti[0].menu, ristoranti[0].numeroPortate, 0);
          else stampaMenuGlutine(ristoranti[0].menu, ristoranti[0].numeroPortate, 1); // Di default ha il glutine.
        }
        break;

      case 0:
        printf("Arrivederci.\n");
        break;

      default:
        printf("Scelta non valida.\n");

    }
    printf("\n");
  } while (s != 0);

  // getchar();
  // system("pause");
  return 0;
}

int menu () {
  printf("1) Stampa Ristorante\n");
  printf("2) Aggiungi portata\n");
  printf("3) Elimina portata\n");
  printf("4) Ricerca portata\n");
  printf("5) Ordina portate\n");
  printf("6) Stampa portate\n");
  printf("7) Stampa portate, filtra per glutine\n");
  printf("0) Esci\n");
  printf("Scelta: ");

  int s;
  scanf("%d", &s);
  return s;
}

void stampaRistorante(ristorante ristorante) {
  printf("Nome: %s\n", ristorante.nome);
  printf("Localita: %s\n", ristorante.localita);
  printf("Coperti: %d\n", ristorante.coperti);
  printf("Stelle: %d\n", ristorante.stelle);
  stampaMenuGlutine(ristorante.menu, ristorante.numeroPortate, -1);
}

void aggiungiPortata(portata menu[NP], int *numeroPortate) {
  if (*numeroPortate >= NP) {
    printf("Spazio nel menu esaurito.\n");
    return;
  }

  portata *portata = &menu[*numeroPortate];

  printf("Inserire nome portata: ");
  scanf("%s", portata->nome);

  printf("Inserire costo portata: ");
  scanf("%f", &portata->costo);

  fflush(stdin);

  char c;
  printf("Glutine? (Y/N) ");
  scanf(" %c", &c);

  if (c == 'n' || c == 'N') portata->glutine = 0;
  else portata->glutine = 1; // Di default ha il glutine.

  (*numeroPortate)++;
}

void eliminaPortata(portata menu[NP], int *numeroPortate) {
  int index = ricercaPortata(menu, *numeroPortate);

  if (index == -1) {
    printf("Portata non trovata.\n");
    return;
  }

  for (int i = index; i < (*numeroPortate)-1; i++) {
    menu[i] = menu[i+1];
  }

  printf("Portata eliminata.\n");

  (*numeroPortate)--;
}

int ricercaPortata(portata menu[NP], int numeroPortate) {
  char buffer[LS];
  printf("Inserire nome: ");
  scanf("%s", buffer);

  for (int i=0; i<numeroPortate; i++) {
    if (strcmp(buffer, menu[i].nome) == 0) return i;
  }

  return -1;
}

void ordinaPortate(portata menu[NP], int numeroPortate) {
  int s;

  printf("Ricerca per (1) nome o (2) glutine? ");
  scanf("%d", &s);

  if (s == 1) {
    for (int i=0; i<numeroPortate-1; i++) {
      for (int j=i; j<numeroPortate; j++) {
        if (strcmp(menu[i].nome, menu[j].nome) > 0) {
          portata t = menu[i];
          menu[i] = menu[j];
          menu[j] = t;
        }
      }
    }
  }

  if (s == 2) {
    for (int i=0; i<numeroPortate-1; i++) {
      for (int j=i; j<numeroPortate; j++) {
        if (menu[i].glutine > menu[j].glutine) {
          portata t = menu[i];
          menu[i] = menu[j];
          menu[j] = t;
        }
      }
    }
  }
}

void stampaPortata(portata portata) {
  printf("%-20s%-10.2f%s\n", portata.nome, portata.costo, portata.glutine ? "Con Glutine" : "Senza Glutine");
}

void stampaMenuGlutine(portata menu[NP], int numeroPortate, int glutine) {
  printf("Menu:\n");
  for (int i=0; i<numeroPortate; i++) {
    if (glutine != -1 && menu[i].glutine != glutine) {
      continue;
    }

    printf("\t");
    stampaPortata(menu[i]);
  }
}

void caricaDati(ristorante ristoranti[NR], int index) {
  FILE *file;
  char path[32], buffer[LS*2];
  sprintf(path, "data/#106/ristorante_%03d.txt", index);

  // Creo se non esiste
  file = fopen(path, "a");
  fclose(file);

  // Leggo fino ad EOF. Non controllo errori di scanf (return == 3) o di letture (ferror()).
  file = fopen(path, "r");

  if (fgets(buffer, LS, file) == NULL) return;
  sscanf(buffer, "Nome=%s", ristoranti[index].nome);

  if (fgets(buffer, LS, file) == NULL) return;
  sscanf(buffer, "Localita=%s", ristoranti[index].localita);

  if (fgets(buffer, LS, file) == NULL) return;
  sscanf(buffer, "Coperti=%d", &ristoranti[index].coperti);

  if (fgets(buffer, LS, file) == NULL) return;
  sscanf(buffer, "Stelle=%d", &ristoranti[index].stelle);

  printf("%s\n", "asdas");

  int numeroPortate = 0;
  portata *portate = ristoranti[index].menu;

  while (fgets(buffer, LS*2, file) != NULL) {
    sscanf(buffer, "%d, %f, %s", &((portate + numeroPortate)->glutine), &((portate + numeroPortate)->costo), (portate + numeroPortate)->nome);
    numeroPortate++;
  }

  ristoranti[index].numeroPortate = numeroPortate;
  fclose(file);
}

void salvaDati(ristorante ristoranti[NR], int index) {
  FILE *file;
  char path[32];
  sprintf(path, "data/#106/ristorante_%03d.txt", index);

  // Sovrascrivo tutti i dati.
  file = fopen(path, "w");
  fprintf(file, "Nome=%s\n", ristoranti[index].nome);
  fprintf(file, "Localita=%s\n", ristoranti[index].localita);
  fprintf(file, "Coperti=%d\n", ristoranti[index].coperti);
  fprintf(file, "Stelle=%d\n", ristoranti[index].stelle);

  portata *portate = ristoranti[index].menu;
  for (int i=0; i<ristoranti[index].numeroPortate; i++) fprintf(file, "%d, %f, %s\n", (portate + i)->glutine , (portate + i)->costo, (portate + i)->nome);
  fclose(file);
}
