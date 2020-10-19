/*
 * Jacopo Del Granchio
 * #103-2 29.02.2020
 *
 * File binari.
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

// Prototipi

#define N 6
#define LS 32

typedef struct {
  int giorni;
  char nome[20];
} mese;

// Funzioni
int main() {
  setlocale(LC_ALL, "");

  mese anno[12];

  // Output

  /*

  FILE *fileOutput = fopen("data/#104/struct.dat", "wb");
  if (fileOutput == NULL) {
    printf("Errore di I/O.\n");
    return 0;
  }

  for (int i=0; i<12; is++) {
    anno[i].giorni = i * 10;
    strcpy(anno[i].nome, "ASDF");
    fwrite(&anno[i], sizeof(mese), 1, fileOutput);
  }

  fclose(fileOutput);

  */

  // Input

  FILE *fileInput = fopen("data/#104/struct.dat", "rb");
  if (fileInput == NULL) {
    printf("Errore di I/O.\n");
    return 0;
  }

  // Carico le struct
  for (int i=0; i<12; i++) {
    fread(&anno[i], sizeof(mese), 1, fileInput);
  }

  for (int i=0; i<12; i++) {
    printf("%s -> %d\n", anno[i].nome, anno[i].giorni);
  }

  fclose(fileInput);

  // getchar();
  // system("pause");
  return 0;
}
