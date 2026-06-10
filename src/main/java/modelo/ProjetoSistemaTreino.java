/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package modelo;

import enums.DayWeek;
import enums.Level;
import enums.MuscleGroups;
import java.time.LocalDate;


/**
 *
 * @author Luiz Guilherme
 */


public class ProjetoSistemaTreino {

    public static void main(String[] args) {
        
         User usuario = new User(1,"Luiz Guilherme","luiz@email.com","123456",20,70.0,1.75,Level.INTERMEDIÁRIO);
//         
         ExerciseRepetition pushUp = new ExerciseRepetition(1,"Push Up","Flexão tradicional",MuscleGroups.PEITO,4,12);
         ExerciseRepetition pullUp = new ExerciseRepetition(2,"Pull Up","Barra fixa com pegada pronada",MuscleGroups.COSTAS,4,8);
         ExerciseRepetition chinUp = new ExerciseRepetition(3,"Chin Up","Barra fixa com pegada supinada", MuscleGroups.COSTAS, 3, 10);
         ExercicioIsometrico prancha = new ExercicioIsometrico(4,"Prancha","Estabilidade do core",MuscleGroups.CORE);
         prancha.setTempoSegundos(60);
         
         Training treinoPullAbs = new Training(1, "Treino de puxada",DayWeek.SEGUNDA);
         treinoPullAbs.setUsuario(usuario);
         treinoPullAbs.getExerciseList().add(pullUp);
         treinoPullAbs.getExerciseList().add(chinUp);
         treinoPullAbs.getExerciseList().add(prancha);
         
         Historico historico = new Historico(1, usuario, LocalDate.of(2026, 05, 29), "Treino realizado.") ;
         
         TrainingSession sessao = new TrainingSession(1, treinoPullAbs, LocalDate.of(2026, 05, 29), 60) ;
         
         ExerciseRecord registroPullUp = new ExerciseRecord(1, sessao, pullUp, "Foram feitas 4 series de 8 repeticoes");
         ExerciseRecord registroChinUp = new ExerciseRecord(2, sessao, chinUp, "Foram feitas 3 series de 10 repeticoes");
         ExerciseRecord registroPrancha = new ExerciseRecord(2, sessao, prancha, "60 segundos (Um minuto)");
         
        System.out.println(usuario);
        
        System.out.println(treinoPullAbs);
        
        for(Exercise e : treinoPullAbs.getExerciseList()){
            System.out.println("\nID: " + e.getId());
            System.out.println("Nome: " + e.getName());
            System.out.println("Volume de treino: " + e.calculateVolume());
        }
        
        System.out.println(historico);
        
        System.out.println(registroPullUp);
        System.out.println(registroChinUp);
        System.out.println(registroPrancha);
    }
}
