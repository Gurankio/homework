/*
 * Jacopo Del Granchio
 * #100 26.02.2020
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

// costanti
#define N 4
#define M 3

// Prototipi
void rimuoviColori(int orig[N*M][3], int grigio[N*M]);
void contorna(int grigio[N*M], int cont[N*M+2*N+2*M+4]);

// Funzioni
int main() {
  setlocale(LC_ALL, "");

  int orig[N*M][3] = {
    {255, 255, 255},
    {0, 0, 0},
    {0, 0, 0},
    {127, 127, 127},
    {127, 127, 127},
    {127, 127, 127},
    {255, 0, 0},
    {0, 255, 0},
    {0, 255, 255},
    {0, 0, 255},
    {255, 255, 0},
    {255, 0, 255}
  };

  int grigio[N*M] = { 0 };
  rimuoviColori(orig, grigio);

  printf("\n");
  for (int i=0; i<N*M; i++) {
    printf("%d\n", grigio[i]);
  }
  printf("\n");

  int cont[N*M+2*N+2*M+4] = { 0 };
  contorna(grigio, cont);

  printf("\n");
  for (int i=0; i<N*M+2*N+2*M+4; i++) {
    printf("%d\n", cont[i]);
  }
  printf("\n");

  // getchar();
  // system("pause");
  return 0;
}

void rimuoviColori(int orig[N*M][3], int grigio[N*M]) {
  for (int i=0; i<N*M; i++) {
    for (int j=0; j<3; j++) {
      grigio[i] += orig[i][j];
    }
    grigio[i] /= 3;
  }
}

void contorna(int grigio[N*M], int cont[N*M+2*N+2*M+4]) {
  int out = 0;
  for (int i=0; i<2+M; i++) {
    cont[out++] = 255;
  }

  for (int i=0; i<N; i++) {
    cont[out++] = 255;
    for (int j=0; j<M; j++) {
      cont[out++] = grigio[i * N + j];
    }
    cont[out++] = 255;
  }

  for (int i=0; i<2+M; i++) {
    cont[out++] = 255;
  }
}
