/*
 * Jacopo Del Granchio
 * #108 25.03.2020
 * Bibioteca.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <locale.h>
#include <stdarg.h>
#include <stdbool.h>
#include <strings.h>
#include <ctype.h>

#define NS 100
#define LS 32

typedef struct {
  char nome[LS];
  int ombrelloni;
  float tariffa;
  int ristorante;
} stabilimento;

// Prototipi
// Menu Principale
void menu(stabilimento[], int*);

void aggiungiStabilimento(stabilimento[], int*);
void ricercaNome(stabilimento[], int*);
void cancellaStabilimento(stabilimento[], int*);
void stampaStabilimento(stabilimento);
void stampaStabilimenti(stabilimento[], int*); // void filtraRistorante();
void stampaPrezzoMinimo(stabilimento[], int*);
void stampaPrezzoMedio(stabilimento[], int*);

// I/O
void carica(stabilimento[], int *);
void salva(stabilimento[], int);

void inputPulito(char *, char *);

// Funzioni
int main() {
  setlocale(LC_ALL, "");

  stabilimento dati[NS];
  int n = 0;

  carica(dati, &n);
  menu(dati, &n);
  return 0;
}

void menu(stabilimento dati[], int *n) {
  int s;
  char buffer[LS];

  void (*funzioni[6])(stabilimento[], int*) = {
    aggiungiStabilimento,
    ricercaNome,
    cancellaStabilimento,
    stampaStabilimenti,
    stampaPrezzoMinimo,
    stampaPrezzoMedio
  };

  do {
    putchar('\n');
    printf("   Stabilimenti Balneari\n");
    printf("1) Aggiungi stabilimento\n");
    printf("2) Ricerca stabilimento\n");
    printf("3) Cancella stabilimento\n");
    printf("4) Stampa stabilimenti\n");
    printf("5) Stampa prezzo minimo\n");
    printf("6) Stampa prezzi medi\n");
    printf("0) Esci\n");
    inputPulito("\nInput: ", buffer);
    s = atoi(buffer);

    if (s < 0 || s > 6) printf("Scelta non valida.\n");
    else if (s != 0) funzioni[s - 1](dati, n);
  } while (s != 0);
}

void aggiungiStabilimento(stabilimento dati[], int *n) {
  if (*n == NS) {
    printf("Spazio esaurito.\n");
    return;
  }

  char buffer[LS];

  inputPulito("Inserire il nome: ", buffer);
  strcpy(dati[*n].nome, buffer);

  inputPulito("Inserire la quantita di ombrelloni: ", buffer);
  dati[*n].ombrelloni = atoi(buffer);

  inputPulito("Inserirela tariffa: ", buffer);
  dati[*n].tariffa = atof(buffer);

  inputPulito("Servizio ristorante? (S/N) ", buffer);
  if (buffer[0] == 's' || buffer[0] == 'S') dati[*n].ristorante = 1;
  else dati[*n].ristorante = 0;

  (*n)++;

  salva(dati, *n);
  printf("Stabilimento aggiunto.\n");
}

void ricercaNome(stabilimento dati[], int *n) {
  if (*n == 0) {
    printf("Nessuno stabilimento.\n");
    return;
  }

  char buffer[LS];

  inputPulito("Inserire il nome da ricercare: ", buffer);
  printf("%-*s%-12s%-12s%-12s\n", LS, "Nome", "Ombrelloni", "Tariffa", "Ristorante");

  for (int i=0; i<*n; i++) {
    if (strcasestr(dati[i].nome, buffer) != NULL) {
      stampaStabilimento(dati[i]);
    }
  }
}

void cancellaStabilimento(stabilimento dati[], int *n) {
  if (*n == 0) {
    printf("Nessuno stabilimento.\n");
    return;
  }

  char buffer[LS];

  inputPulito("Inserire il nome da ricercare: ", buffer);

  int i;
  for (i=0; i<*n; i++) {
    if (strcasecmp(dati[i].nome, buffer) == 0) break;
  }

  if (i == *n) {
    printf("Nessun risultato.\n");
    return;
  }

  for (int j=i; j<(*n)-1; j++) {
    dati[j] = dati[j+1];
  }

  (*n)--;

  salva(dati, *n);
  printf("Stabilimento cancellato.\n");
}

void stampaStabilimento(stabilimento dati) {
  printf("%-*s%-12d%-12.2f%-12s\n", LS, dati.nome, dati.ombrelloni, dati.tariffa, dati.ristorante ? "Si" : "No");
}

void stampaStabilimenti(stabilimento dati[], int *n) {
  if (*n == 0) {
    printf("Nessuno stabilimento.\n");
    return;
  }

  int ristorante = 0;
  char buffer[LS];

  inputPulito("Filtrare per il servizio ristorante? (S/N) ", buffer);
  if (buffer[0] == 's' || buffer[0] == 'S') ristorante = 1;

  printf("\n%-*s%-12s%-12s%-12s\n", LS, "Nome", "Ombrelloni", "Tariffa", "Ristorante");

  for (int i=0; i<*n; i++) {
    if (ristorante) {
      if (dati[i].ristorante) stampaStabilimento(dati[i]);
    } else stampaStabilimento(dati[i]);
  }
}

void stampaPrezzoMinimo(stabilimento dati[], int *n) {
  if (*n == 0) {
    printf("Nessuno stabilimento.\n");
    return;
  }

  float min = 0;
  int minI = 0;

  for (int i=0; i<*n; i++) {
    if (min > dati[i].tariffa) {
      min = dati[i].tariffa;
      minI = i;
    }
  }

  printf("Stabilimento con la tariffa minore: \n");
  printf("%-*s%-12s%-12s%-12s\n", LS, "Nome", "Ombrelloni", "Tariffa", "Ristorante");
  stampaStabilimento(dati[minI]);
}

void stampaPrezzoMedio(stabilimento dati[], int *n) {
  if (*n == 0) {
    printf("Nessuno stabilimento.\n");
    return;
  }

  float somma = 0;
  float sommaRistorante = 0;
  int contaRistorante = 0;

  for (int i=0; i<*n; i++) {
    somma += dati[i].tariffa;
    if (dati[i].ristorante) {
      sommaRistorante += dati[i].tariffa;
      contaRistorante++;
    }
  }

  printf("Tariffa media: %.2f\n", somma / *n);
  printf("Tariffa media senza ristorante: %.2f\n", (somma - sommaRistorante) / (*n - contaRistorante));
  printf("Tariffa media con ristorante: %.2f\n", sommaRistorante / contaRistorante);
}

void carica(stabilimento dati[], int *n) {
  FILE *file = fopen("./data/#109/stabilimenti.txt", "a");

  fclose(file);
  file = fopen("./data/#109/stabilimenti.txt", "r");

  if (file == NULL) {
    printf("Errore di I/O.\n");
    return;
  }

  *n = 0;
  int i = 0;
  char buffer[4][LS];

  while (fgets(buffer[i % 4], LS, file) != NULL) {
    buffer[i % 4][strlen(buffer[i % 4])-1] = '\0';

    if (++i % 4 == 0) {
      strcpy(dati[*n].nome, buffer[0]);
      dati[*n].ombrelloni = atoi(buffer[1]);
      dati[*n].tariffa = atof(buffer[2]);
      dati[*n].ristorante = atoi(buffer[3]);
      (*n)++;
    }
  }

  if (i / 4 != *n) {
    printf("Errore in lettura. Dei dati potrebbero essero corrotti.\n");
  }

  fclose(file);
}

void salva(stabilimento dati[], int n) {
  FILE *file = fopen("./data/#109/stabilimenti.txt", "w");

  if (file == NULL) {
    printf("Errore di I/O.\n");
    return;
  }

  char buffer[LS];

  for (int i=0; i<n; i++) {
    fprintf(file, "%s\n", dati[i].nome);
    fprintf(file, "%d\n", dati[i].ombrelloni);
    fprintf(file, "%f\n", dati[i].tariffa);
    fprintf(file, "%d\n", dati[i].ristorante);
  }

  fclose(file);
}

// Rimuove \n, trim da destra, trim da sinistra.
void inputPulito(char *outputString, char *inputBuffer) {
  printf("%s", outputString);
  int ripeti;

  do {
    ripeti = 0;
    fgets(inputBuffer, LS, stdin);

    if (strlen(inputBuffer) == LS - 1 && inputBuffer[LS - 2] != '\n') {
      fgets(inputBuffer, LS, stdin);
      printf("Input troppo largo. Rinserisci: ");
      ripeti = 1;
    }
  } while (ripeti);

  inputBuffer[strlen(inputBuffer)-1] = '\0';

  for (int i = strlen(inputBuffer); isspace(inputBuffer[i]); i--)
    inputBuffer[i] = '\0';

  while (isspace(inputBuffer[0]))
    for (int i = 0; i < strlen(inputBuffer); i++)
      inputBuffer[i] = inputBuffer[i + 1];
}
