//
//  main.c
//  ForkJoin
//
//  Created by Gurankio on 19/04/2021.
//

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#ifdef __linux__
#include <sys/types.h>
#include <sys/wait.h>
#endif

#define join(pid) waitpid(pid, 0, 0)
#define join_cont(N, pids) { for (int i=0; i<N; i++) join(pids[i]); }
#define fork_cont(N, out_pids, child_functions) { for (int i=0; i<N; i++) if ((out_pids[i] = fork()) == 0) child_functions[i](); }
#define main int main(int argc, const char * argv[])
#define begin {
#define processo(nome) void nome(void)
#define end_return(n) exit(n); }
#define end end_return(0)

processo(calcoloPadre);
processo(calcoloFiglioUno);
processo(calcoloFiglioDue);
processo(calcoloFiglioTre);

const int SIZE = 3;
int pid_figli[SIZE] = {0, 0, 0};
void (*funzioni_figli[SIZE])(void) = {calcoloFiglioUno, calcoloFiglioDue, calcoloFiglioTre};

main
begin
    fork_cont(SIZE, pid_figli, funzioni_figli);
    calcoloPadre();
end

processo(calcoloPadre)
begin
printf("Sasso P: 9\n");
    join_cont(SIZE, pid_figli);
end

processo(calcoloFiglioUno)
begin
    printf("Sasso 1\n");
end

processo(calcoloFiglioDue)
begin
    printf("Sasso 2\n");
end_return(42)

processo(calcoloFiglioTre)
begin
    printf("Sasso 3\n");
end
