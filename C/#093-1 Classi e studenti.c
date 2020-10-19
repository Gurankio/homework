/*
 * Jacopo Del Granchio
 * #093 12.02.2020
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <locale.h>
#include <stdarg.h>
#include <stdbool.h>
#include <string.h>

// Costanti
#define NC 10
#define NS 50
#define LS 20

// Macro
#define chiedi(msg, format, ...) \
  printf(msg); \
  scanf(format, __VA_ARGS__);

// Prototipi
int menu();

void aggiungiClasse(char [NC][NS][LS], int *);
int cercaClasse(char [NC][NS][LS], int);
void rimuoviClasse(char [NC][NS][LS], int *, int[]);
void stampaClassi(char [NC][NS][LS], int, int[]);

int menuClasse();

void aggiungiStudente(char [NS][LS], char [NS][4][LS],  int *);
int cercaStudente(char [NS][LS], char [NS][4][LS],  int);
void rimuoviStudente(char [NS][LS], char [NS][4][LS],  int *);
void stampaStudenti(char [NS][LS], char [NS][4][LS],  int);

// Funzioni
int main() {
  setlocale(LC_ALL, "");

  char classi[NC][NS][LS];
  int cClassi = 0, cStudenti[NC] = { 0 };

  // Informazioni extra:
  // 0: Sesso
  // 1: Data di nascita
  // 2: Citta' di nascita
  // 3: TODO: Codice Fiscale
  char extra[NC][NS][4][LS];

  int s;
  do {
    s = menu();

    switch (s) {
      case 1:
        aggiungiClasse(classi, &cClassi);
        break;

      case 2:
        printf("\n");
        int classe = cercaClasse(classi, cClassi);

        do {
          s = menuClasse();

          switch (s) {
            case 1:
              aggiungiStudente(classi[classe], extra[classe], &cStudenti[classe]);
              break;

            case 2:
              printf("\n");
              int studente = cercaStudente(classi[classe], extra[classe], cStudenti[classe]);
              printf("%d\n", studente);
              break;

            case 3:
              rimuoviStudente(classi[classe], extra[classe], &cStudenti[classe]);
              break;

            case 4:
              stampaStudenti(classi[classe], extra[classe], cStudenti[classe]);
              break;

            case 0:
              printf("Esco dalla classe %s\n", classi[classe][0]);
              break;

            default:
              printf("Scelta non valida.\n");
          }
        } while (s != 0);
        s = -1;
        break;

      case 3:
        rimuoviClasse(classi, &cClassi, cStudenti);
        break;

      case 4:
        stampaClassi(classi, cClassi, cStudenti);
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
  printf("\n");
  printf("   Menu Principale \n");
  printf("1) Aggiungi classe \n");
  printf("2) Cerca classe    \n");
  printf("3) Rimuovi classe  \n");
  printf("4) Stampa riepilogo\n");
  printf("0) Esci            \n");
  printf("Scelta: ");

  int s;
  char nl;
  scanf("%d", &s);
  scanf("%c", &nl);
  printf("\n");
  return s;
}

void aggiungiClasse(char classi[NC][NS][LS], int *cClassi) {
  printf("Inserire il nome della nuova classe: ");
  gets(classi[(*cClassi)++][0]);
}

int cercaClasse(char classi[NC][NS][LS], int cClassi) {
  char buffer[LS];

  printf("Inserire il nome della classe da cercare: ");
  gets(buffer);

  for (int i = 0; i < cClassi; i++)
    if (strcmp(buffer, classi[i][0]) == 0) return i;

  return -1;
}

void rimuoviClasse(char classi[NC][NS][LS], int *cClassi, int cStudenti[]) {
  int classe = cercaClasse(classi, *cClassi);

  if (classe == -1) {
    printf("Classe non trovata.\n");
    return;
  }

  for (int i = classe; i < *cClassi - 1; i++)
    for (int j = 0; j < cStudenti[classe] + 1; j++)
      strcpy(classi[i][j], classi[i + 1][j]);

  printf("Classe rimossa.\n");
  (*cClassi)--;
}

void stampaClassi(char classi[NC][NS][LS], int cClassi, int cStudenti[]) {
  if (cClassi == 0) return;

  int max = cStudenti[0];

  for (int i = 1; i < cClassi; i++)
    if (cStudenti[i] > max) max = cStudenti[i];

  for (int j = 0; j < max + 1; j++) {
    for (int i = 0; i < cClassi; i++)
      printf("%*s", LS, cStudenti[i] >= j ? classi[i][j] : "");

    printf("\n");
  }
}

int menuClasse() {
  printf("\n");
  printf("   Menu Classe      \n");
  printf("1) Aggiungi studente\n");
  printf("2) Cerca studente   \n");
  printf("3) Rimuovi studente \n");
  printf("4) Stampa riepilogo \n");
  printf("0) Esci             \n");
  printf("Scelta: ");

  int s;
  char nl;
  scanf("%d", &s);
  scanf("%c", &nl);
  printf("\n");
  return s;
}

void aggiungiStudente(char classi[NS][LS], char extra[NS][4][LS], int *cStudenti) {
  (*cStudenti)++;

  printf("Inserire il nome dello studente: ");
  gets(classi[*cStudenti]);

  printf("Inserire il sesso dello studente: ");
  gets(extra[*cStudenti][0]); // TODO: input migliorato.

  printf("Inserire la data di nascita dello studente: ");
  gets(extra[*cStudenti][1]);

  printf("Inserire la citta di nascita dello studente: ");
  gets(extra[*cStudenti][2]);
}

int cercaStudente(char classi[NS][LS], char extra[NS][4][LS], int cStudenti) {
  char buffer[LS];

  printf("Inserire il nome dello studente da cercare: ");
  gets(buffer);

  for (int i = 1; i < cStudenti + 1; i++)
    if (strcmp(buffer, classi[i]) == 0) return i;

  return -1;
}

void rimuoviStudente(char classi[NS][LS], char extra[NS][4][LS], int *cStudenti) {
  int studente = cercaStudente(classi, extra, *cStudenti);

  if (studente == -1) {
    printf("Studente non trovato.\n");
    return;
  }

  for (int i = studente; i < *cStudenti; i++)
    strcpy(classi[i], classi[i + 1]);

  printf("Studente rimosso.\n");
  (*cStudenti)--;
}

void stampaStudenti(char classi[NS][LS], char extra[NS][4][LS], int cStudenti) {
  printf("%*s\n", LS, classi[0]);
  printf("%*s%*s%*s%*s\n", LS, "Nome", LS, "Sesso", LS, "Data di nascita", LS, "Citta di nascita");

  for (int i = 1; i < cStudenti + 1; i++) {
    printf("%*s", LS, classi[i]);

    for (int j = 0; j < 3; j++)
      printf("%*s", LS, extra[i][j]);

    printf("\n");
  }
}
