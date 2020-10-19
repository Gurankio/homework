/*
 * Jacopo Del Granchio
 * #051 27.11.2019
 *
 * Risolve una eqazione di secondo grado dati i coefficenti.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <locale.h>
#include <stdarg.h>

// Prototipi
void chiedi(char *, char *, ...);
int delta(int, int, int);
int primoGrado(int, int, int *);
void soluzioni(int, int, int, int *, int *);

// Funzioni
int main() {
  setlocale(LC_ALL, "");

  int a, b, c, r1, r2;
  chiedi("Inserisci il coefficente di x al quadrato: ", "%d", &a);
  chiedi("Inserisci il coefficente di x: ", "%d", &b);
  chiedi("Inserisci il termine noto: ", "%d", &c);

  if (a == 0) {
    int esiste = primoGrado(b, c, &r1);

    if (esiste) printf("La soluzione è %d\n", r1);
    else printf("Non esistono soluzioni\n");
  } else {
    int d = delta(a, b, c);

    if (d != -1) {
      soluzioni(a, b, d, &r1, &r2);
      printf("Le soluzioni sono %d e %d\n", r1, r2);
    } else printf("Non esistono soluzioni\n");
  }

  // getchar();
  // system("pause");
  return 0;
}

void chiedi(char *msg, char *format, ...) {
  printf(msg);
  va_list list;
  va_start(list, format);
  vscanf(format, list);
  va_end(list);
}

int delta(int a, int b, int c) {
  int t = b * b - 4 * a * c;

  if (t >= 0) return t;
  else return -1;
}

int primoGrado(int b, int c, int *r) {
  if (b != 0) {
    float t = -c / b;
    *r = (int)t;
    return 1;
  }

  return 0;
}

void soluzioni(int a, int b, int d, int *r1, int *r2) {
  float t1 = (-1 * b + sqrt(d)) / (2 * a);
  float t2 = (-1 * b - sqrt(d)) / (2 * a);

  *r1 = (int)t1;
  *r2 = (int)t2;
}
