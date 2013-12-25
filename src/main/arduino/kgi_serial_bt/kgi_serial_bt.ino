int i = 0;
int control;
int in = 0;
int counter = 0;

HardwareSerial ser = Serial1;

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
  //pinMode(1, OUTPUT); 
 // pinMode(0, OUTPUT); 
  ser.begin(9600);
 
}

void loop() {
     
       
       i =  ser.read();
       if( i != -1 ){
         if( i == 's'){
           ser.println("s");
           control = 0;
           counter = 0;         
         }else{
           if( counter ==0){        
              control = control + toInt( i, 10000);              
           }
           if( counter ==1 ){
              control = control + toInt( i, 1000);
           }
           if( counter == 2){
             control = control + toInt( i, 100);
           }
           if( counter == 3){
              control = control + toInt( i, 10);
           }
           if( counter == 4){
             control = control + toInt( i, 1);
             ser.print("c: ");
             ser.println(control, DEC);
             flipOuts( control);
             ser.print("z");
           }
  
          counter = counter +1;              
         }

       }
}

void flipOuts( int cn ){
       flip(  cn, 8192,13  );
       flip(  cn, 4096,12  );
       flip(  cn, 2048,11  );
       flip(  cn, 1024,10  );
       flip(  cn, 512,9  );
       flip(  cn, 256,8  );
       flip(  cn, 128,7  );
       flip(  cn, 64,6  );
       flip(  cn, 32,5  );
       flip(  cn, 16,4  );
       flip(  cn, 8,3  );
       flip(  cn, 4,2  );
       flip(  cn, 2,1  );
       flip(  cn, 1,0  );
}

void flip( int c, int check, int p) {
  //     Serial.print("pin: ");
  //     Serial.print(p, DEC);
  //     Serial.print(" check ");
  //     Serial.print(check, DEC);
      if(  (c & check) > 0  ){
        digitalWrite( p, HIGH);
  //        Serial1.println(" H");
       }else{
         digitalWrite( p, LOW);
 //        Serial1.println(" L");
       }
}

int toInt( int c, int m ){
   return (c-48)*m;
}
