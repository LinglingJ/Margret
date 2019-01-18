/* Coding Assignment 10 */
//Maze Game
// Uses direction and map file.

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#define MAPSIZE 10

char MapFilename[26]={};
struct PlayerInfo{
    char PlayerName[20];
    char PlayerName2[20];
    int CurrentRow;
    int CurrentCol;
};


int MoveNorth(char Map[MAPSIZE][MAPSIZE], struct PlayerInfo *PlayerPtr){
    
    if (((PlayerPtr->CurrentRow-1)>=0)&&(Map[PlayerPtr->CurrentRow-1][PlayerPtr->CurrentCol]=='X')||(Map[PlayerPtr->CurrentRow-1][PlayerPtr->CurrentCol]=='S')||(Map[PlayerPtr->CurrentRow-1][PlayerPtr->CurrentCol]=='E'))
    {
        (PlayerPtr->CurrentRow)--;
        return 1;
    }
    else{
        printf("North is the wrong move\n"); 
        return 0;
    }
}
int MoveSouth(char Map[MAPSIZE][MAPSIZE], struct PlayerInfo *PlayerPtr){
    if (((PlayerPtr->CurrentRow+1)<10)&&(Map[PlayerPtr->CurrentRow+1][PlayerPtr->CurrentCol]=='X')||(Map[PlayerPtr->CurrentRow+1][PlayerPtr->CurrentCol]=='S')||(Map[PlayerPtr->CurrentRow+1][PlayerPtr->CurrentCol]=='E'))
    {
        (PlayerPtr->CurrentRow)++;
        return 1;
    }
    else{
        printf("South is the wrong move\n"); 
        return 0;
    }
}
int MoveEast(char Map[MAPSIZE][MAPSIZE], struct PlayerInfo *PlayerPtr){
    if (((PlayerPtr->CurrentCol+1)<10)&&(Map[PlayerPtr->CurrentRow][PlayerPtr->CurrentCol+1]=='X')||(Map[PlayerPtr->CurrentRow][PlayerPtr->CurrentCol+1]=='S')||(Map[PlayerPtr->CurrentRow][PlayerPtr->CurrentCol+1]=='E'))
    {
        (PlayerPtr->CurrentCol)++;
        return 1;
    }
    else{
        printf("East is the wrong move\n"); 
        return 0;
    }
}

int MoveWest(char Map[MAPSIZE][MAPSIZE], struct PlayerInfo *PlayerPtr){
    
    if (((PlayerPtr->CurrentCol-1)>=0)&&(Map[PlayerPtr->CurrentRow][PlayerPtr->CurrentCol-1]=='X')||(Map[PlayerPtr->CurrentRow][PlayerPtr->CurrentCol-1]=='S')||(Map[PlayerPtr->CurrentRow][PlayerPtr->CurrentCol-1]=='E'))
    {
        (PlayerPtr->CurrentCol)--;
        return 1;
    }
    else{
        printf("West is the wrong move\n"); 
        return 0;
    }
}


void get_command_line_params(int argc,char *argv[]){
int i;
for(i=1;i<argc;i++){

    if(!memcmp(*(argv+i),"MAP=",4)){
        memcpy(MapFilename,(strchr(*(argv+i),'=')+1),25);
    }
}
if(MapFilename==0){
    printf("MAP= must be given on the command line");
    exit(0);
}   
}



int main(int argc,char *argv[])
{
/*Declaring Variables*/
FILE *TreasureMap;
char MapList[500]={};
char Map[MAPSIZE][MAPSIZE]={};
char PlayerPath[MAPSIZE][MAPSIZE]={};
char PlayerMove;
char buffer[2]={};
char Names[40]={};
int i,j,k,MakeMove,index;
int Player_Num;
int steps[2]={};
int ind;
struct PlayerInfo Player;
struct PlayerInfo *PlayerPtr;
PlayerPtr=&Player;

/*Reading Command Line*/
get_command_line_params(argc,argv);
TreasureMap=fopen(MapFilename,"r+");
if(TreasureMap==NULL){
    perror("TreasureMap did not open");
    clearerr(0);
    exit(0);
    system("clear");
}
/*Bonus*/
printf("Please enter <1> or <2> for the number of players: ");
scanf("%d",&Player_Num);
getchar();



for(index=0;index<Player_Num;index++){
    printf("Enter Player name\n");
    scanf("%s",&Player.PlayerName);
    getchar(); /*to get rid of <enter>*/
   
    fgets(MapList,500,TreasureMap);


    Player.CurrentCol=0;
    Player.CurrentRow=0;

    i=0;
    for(j=0;j<MAPSIZE;j++){
        for(k=0;k<MAPSIZE;k++){
            Map[j][k]=MapList[i];
            printf("%c ",Map[j][k]);
            i++;
            PlayerPath[j][k]='-';
        }
        printf("\n");
    }
    printf("Press <ENTER> when you are ready to start ");
    getchar(); /*To pause and capture enter when pressed*/
    system("clear");

    PlayerPath[0][0]=Map[0][0];

    do
    {
        printf("Enter a direction (NSEW) or 'Q' to quit ");
        scanf("%c",&PlayerMove);
        getchar();    /*??????????????????/*/
        PlayerMove=toupper(PlayerMove);
        system("clear");
        steps[index]=steps[index]+1;
        if(PlayerMove=='N') MakeMove=MoveNorth(Map, PlayerPtr);
        else if(PlayerMove=='S') MakeMove=MoveSouth(Map, PlayerPtr);
        else if(PlayerMove=='W') MakeMove=MoveWest(Map, PlayerPtr);
        else if(PlayerMove=='E') MakeMove=MoveEast(Map, PlayerPtr);
        else{
        printf("Invalid move - must be NSEW\n"); 
        MakeMove=0;
        }
        if(MakeMove){
            if(Map[Player.CurrentRow][Player.CurrentCol]=='S'){
                printf("Player x is back at the start\n");    
            }
            if(Map[Player.CurrentRow][Player.CurrentCol]=='X'){
                PlayerPath[Player.CurrentRow][Player.CurrentCol]=Map[Player.CurrentRow][Player.CurrentCol];    
            }
            if(Map[Player.CurrentRow][Player.CurrentCol]=='E'){
                printf("Player x has made it to the end ");
                if(Player_Num==1) printf("- WINNER!!\n");
                PlayerPath[Player.CurrentRow][Player.CurrentCol]=Map[Player.CurrentRow][Player.CurrentCol];  
                PlayerMove='Q';   
            }
        }
        /*Print PlayPath*/
        printf("\n");
        for(j=0;j<MAPSIZE;j++){
            for(k=0;k<MAPSIZE;k++){
                printf("%c ",PlayerPath[j][k]);
            }
            printf("\n");
        }
    }
    while(PlayerMove!='Q');
}
if(Player_Num==2){
    if(steps[0]<steps[1]) printf("Player 1 wins!!! They had the fewest moves!");
    else if(steps[0]>steps[1]) printf("Player 2 wins!!! They had the fewest moves!");
    else printf("It's a tie!!!");
}
printf("\n");


/*Close the files you open*/
fclose(TreasureMap);

return 0;
}


