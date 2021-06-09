//
//  main.c
//  ForkJoin
//
//  Created by Gurankio on 19/04/2021.
//

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/mman.h>
#ifdef __linux__
#include <sys/types.h>
#include <sys/wait.h>
#endif

#define malloc_shared(size) mmap(NULL, size, PROT_READ | PROT_WRITE, MAP_SHARED | MAP_ANONYMOUS, -1, 0)

#define a v[0]
#define b v[1]
#define c v[2]
#define d v[3]
#define e v[4]


int main() {
    printf("Calcolo: (3+2)+(6-4)+(18/9)\n");
    int *v = malloc_shared(sizeof(int) * 5);
    
    if (fork()) {
        if (fork()) {
            b=6-4;
            wait(0); // Wait C
            d=b*c;
            exit(0);
        } else {
            c=18/9;
            exit(0);
        }
    }
    
    a=3+2;
    wait(0); // Wait D
    e=a+d;
    printf("Risultato: %d\n", v[e]);
    exit(0);
}
