/*
 * Jacopo Del Granchio
 * #093-2 12.02.2020
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

typedef struct {
  int anno;
  int mese;
  int giorno;
} data;

typedef struct {
  char nome[LS];
  int sesso;
  data data;
  char luogo[LS];
} studente;

typedef struct {
  char nome[LS];
  studente studenti[NS];
  int numStudenti;
} classe;

// Prototipi
int menu();

void aggiungiClasse(classe [NC], int *);
int cercaClasse(classe [NC], int);
void rimuoviClasse(classe [NC], int *);
void stampaClassi(classe [NC], int);

int menuClasse(classe);

void aggiungiStudente(classe *);
int cercaStudente(classe);
void rimuoviStudente(classe *);
void stampaStudenti(classe);

void stampaStudente(studente);

// Funzioni
int main() {
  setlocale(LC_ALL, "");

  classe classi[NC];
  int numClassi = 0;

  int s;
  do {
    s = menu();

    switch (s) {
      case 1:
        aggiungiClasse(classi, &numClassi);
        break;

      case 2:
        printf("\n");
        int classeIndice = cercaClasse(classi, numClassi);

        if (classeIndice == -1) {
          printf("Classe non trovata.\n");
          break;
        }

        printf("Classe trovata.\n");
        classe *c = &classi[classeIndice];

        do {
          s = menuClasse(*c);

          switch (s) {
            case 1:
              aggiungiStudente(c);
              break;

            case 2:
              printf("\n");
              int studenteIndice = cercaStudente(*c);

              printf("\n");
              printf("%-*s%-*s%-*s%-*s\n", LS, "Nome", LS / 2, "Sesso", LS, "Data di nascita", LS, "Citta di nascita");
              stampaStudente(c->studenti[studenteIndice]);
              break;

            case 3:
              rimuoviStudente(c);
              break;

            case 4:
              stampaStudenti(*c);
              break;

            case 0:
              printf("Esco dalla classe %s\n", c->nome);
              break;

            default:
              printf("Scelta non valida.\n");
          }
        } while (s != 0);
        s = -1;
        break;

      case 3:
        rimuoviClasse(classi, &numClassi);
        break;

      case 4:
        stampaClassi(classi, numClassi);
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

void aggiungiClasse(classe classi[NC], int *numClassi) {
  if (*numClassi >= NC) {
    printf("Massimo numero di classi raggiunto.\n");
    return;
  }

  printf("Inserire il nome della nuova classe: ");
  classi[*numClassi].numStudenti = 0;
  gets(classi[(*numClassi)++].nome);
}

int cercaClasse(classe classi[NC], int numClassi) {
  char buffer[LS];

  printf("Inserire il nome della classe da cercare: ");
  gets(buffer);

  for (int i = 0; i < numClassi; i++)
    if (strcmp(buffer, classi[i].nome) == 0) return i;

  return -1;
}

void rimuoviClasse(classe classi[NC], int *numClassi) {
  if (*numClassi <= 0) {
    printf("Non ci sono classi.\n");
    return;
  }

  int classe = cercaClasse(classi, *numClassi);

  if (classe == -1) {
    printf("Classe non trovata.\n");
    return;
  }

  for (int i = classe; i < *numClassi - 1; i++)
    classi[i] = classi[i + 1];

  printf("Classe rimossa.\n");
  (*numClassi)--;
}

void stampaClassi(classe classi[NC], int numClassi) {
  if (numClassi == 0) {
    printf("Non ci sono classi.\n");
    return;
  }

  for (int i = 0; i < numClassi; i++)
    printf("%-*s", LS, classi[i].nome);

  putchar('\n');

  int max = classi[0].numStudenti;

  for (int i = 1; i < numClassi; i++)
    if (classi[i].numStudenti > max) max = classi[i].numStudenti;

  for (int j = 0; j < max; j++) {
    for (int i = 0; i < numClassi; i++)
      printf("%-*s", LS, classi[i].numStudenti >= j ? classi[i].studenti[j].nome : "");

    putchar('\n');
  }
}

int menuClasse(classe classe) {
  printf("\n");
  printf("   Menu di \"%s\"\n", classe.nome);
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

void aggiungiStudente(classe *classe) {
  if (classe->numStudenti >= NS) {
    printf("Massimo numero di studenti raggiunto.\n");
    return;
  }

  studente *s = &classe->studenti[classe->numStudenti];
  char nl;

  printf("Inserire il nome dello studente: ");
  gets(s->nome);

  do {
    printf("Inserire il sesso (0: Maschio, 1:Femmina) dello studente: ");
    scanf("%d", &s->sesso); // TODO: input migliorato.
    scanf("%c", &nl);

    if (s->sesso < 0 || s->sesso > 1) {
      printf("Input non valido.\n");
    }
  } while (s->sesso < 0 || s->sesso > 1);

  do {
    printf("Inserire la data di nascita (Formato: YYYY.MM.DD) dello studente: ");
    scanf("%d.%d.%d", &s->data.anno, &s->data.mese, &s->data.giorno);
    scanf("%c", &nl);

    if (s->data.mese < 0 || s->data.mese > 12 || s->data.giorno < 0 || s->data.giorno > 31) {
      printf("Data inserita non valida.\n");
    }
  } while (s->data.mese < 0 || s->data.mese > 12 || s->data.giorno < 0 || s->data.giorno > 31); // 31 Febbraio. GG.


  printf("Inserire la citta di nascita dello studente: ");
  gets(s->luogo);

  (classe->numStudenti)++;
}

int cercaStudente(classe classe) {
  char buffer[LS];

  printf("Inserire il nome dello studente da cercare: ");
  gets(buffer);

  for (int i = 0; i < classe.numStudenti; i++)
    if (strcmp(buffer, classe.studenti[i].nome) == 0) return i;

  return -1;
}

void rimuoviStudente(classe *classe) {
  if (classe->numStudenti <= 0) {
    printf("Non esistono studenti.\n");
    return;
  }

  int studente = cercaStudente(*classe);

  if (studente == -1) {
    printf("Studente non trovato.\n");
    return;
  }

  for (int i = studente; i < classe->numStudenti; i++)
    classe->studenti[i] = classe->studenti[i + 1];

  printf("Studente rimosso.\n");
  (classe->numStudenti)--;
}

void stampaStudenti(classe classe) {
  if (classe.numStudenti <= 0) {
    printf("Non ci sono studenti.\n");
    return;
  }

  printf("Classe: %-*s\n", LS, classe.nome);
  printf("%-*s%-*s%-*s%-*s\n", LS, "Nome", LS / 2, "Sesso", LS, "Data di nascita", LS, "Citta di nascita");

  for (int i = 0; i < classe.numStudenti; i++) {
    stampaStudente(classe.studenti[i]);
  }
}

void stampaStudente(studente s) {
  printf("%-*s", LS, s.nome);
  printf("%-*s", LS / 2, s.sesso ? "Maschio" : "Femmina");
  char buffer[LS];
  sprintf(buffer, "%02d.%02d.%d", s.data.giorno, s.data.mese, s.data.anno);
  printf("%-*s", LS, buffer);
  printf("%-*s", LS, s.luogo);
  printf("\n");
}
