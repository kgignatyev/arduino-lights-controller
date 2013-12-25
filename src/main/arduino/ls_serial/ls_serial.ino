int i = 0;
int control;
int in = 0;

void setup() {                
  // initialize the digital pin as an output.
  // Pin 13 has an LED connected on most Arduino boards:
  pinMode(13, OUTPUT);    
  pinMode(12, OUTPUT); 
  pinMode(11, OUTPUT); 
  pinMode(10, OUTPUT); 
  pinMode(9, OUTPUT); 
  pinMode(8, OUTPUT); 
  pinMode(7, OUTPUT); 
  pinMode(6, OUTPUT); 
  pinMode(5, OUTPUT); 
  pinMode(4, OUTPUT); 
  pinMode(3, OUTPUT); 
  pinMode(2, OUTPUT); 
//  pinMode(1, OUTPUT); 
//  pinMode(0, OUTPUT); 
  Serial.begin(9600);
   
}

void loop() {
       if (Serial.available() > 5){
       i =  Serial.read();
       if( i == 's'){
       //  Serial.print("I received: ");
      //   Serial.println(i, DEC);
         control = 0;
         control = control + toInt( readInput(), 10000);
         control = control + toInt( readInput(), 1000);
         control = control + toInt( readInput(), 100);
         control = control + toInt( readInput(), 10);
         control = control + toInt( readInput(), 1);
         Serial.flush();
         Serial.print("c:");
          Serial.print(control, DEC);

       flip(  control, 8192,13  );
       flip(  control, 4096,12  );
       flip(  control, 2048,11  );
       flip(  control, 1024,10  );
       flip(  control, 512,9  );
       flip(  control, 256,8  );
       flip(  control, 128,7  );
       flip(  control, 64,6  );
       flip(  control, 32,5  );
       flip(  control, 16,4  );
       flip(  control, 8,3  );
       flip(  control, 4,2  );
       flip(  control, 2,1  );
       flip(  control, 1,0  );
        Serial.println("z");
       }
       }
}

void flip( int c, int check, int p) {
     //  Serial.print("pin: ");
     //  Serial.print(p, DEC);
     //  Serial.print(" check ");
     //  Serial.print(check, DEC);
      if(  (c & check) > 0  ){
         digitalWrite( p, HIGH);
       //   Serial.println(" H");
       }else{
         digitalWrite( p, LOW);
     //    Serial.println(" L");
       }
}

int readInput(){
  Serial.available();
  in = Serial.read();
 // Serial.print( "got " );
//  Serial.println(in, DEC);
  return in;
}

int toInt( int c, int m ){
   return (c-48)*m;
}
