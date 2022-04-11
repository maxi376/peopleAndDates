import java.text.SimpleDateFormat;
import java.util.*;

public class App {

	public static void main(String args[]) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter nr of people to be read: ");
		String str = sc.nextLine();

		int n = Integer.parseInt(str);

		String[] first = new String[n];
		String[] last = new String[n];
		Date[] dates = new Date[n];
		int j = 0;
		int k = 0;
		int maxj = 0;//biggest number of family members
		int control = 0;
		String aux1;
		Date auxDate;
		Date maxDate;
		//also possible to be implemented with tuplets

		//mask for total number of family members(last name)
		int[] nrFamilyMembers = new int[n];
		int[] copyNrFamilyMembers = new int[n];

		ArrayList<ArrayList<String>> listFamiliiMenbri
				= new ArrayList<ArrayList<String>>();//list for members of families ordered by age
		ArrayList<String> listAux = new ArrayList<String>();//auxiliary arraylist
		ArrayList<String> listAux2 = new ArrayList<String>();//auxiliary arraylist
		ArrayList<String> listLastName = new ArrayList<String>();//list for unique last names

		int[] markedPeople = new int[n];//folosit pentru a cunoaste care persoane au fost parcurse

		for (int i = 0; i < n; i++) {
			str = sc.next();
			first[i] = str;
			str = sc.next();
			last[i] = str;
			str = sc.nextLine();
			//dates[i]=new SimpleDateFormat("dd/MM/yyyy").parse(str);
			try {
				dates[i] = new SimpleDateFormat("yyyy-MM-dd").parse(str);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		//sort based on dates
		for (int i = 0; i < n - 1; i++) {
			//initializing
			maxDate = dates[i];
			j = 0;

			//actual ascending sort based on dates
			for (k = i + 1; k < n; k++) {
				//the oldest
				if (maxDate.compareTo(dates[k]) > 0) {
					maxDate = dates[k];
					j = k;
				}
			}


			if (maxDate.compareTo(dates[i]) != 0 && j != i) {
				aux1 = first[i];
				first[i] = first[j];
				first[j] = aux1;

				aux1 = last[i];
				last[i] = last[j];
				last[j] = aux1;

				auxDate = dates[i];
				dates[i] = dates[j];
				dates[j] = auxDate;
			}
		}

		/*for(int i=0; i<n; i++) {
			System.out.print(first[i]+" ");
			System.out.print(last[i]+" ");
			System.out.println(dates[i]);
		}*/ //testing debugging grounds

		for (int i = 0; i < n; i++) {
			nrFamilyMembers[i] = 0;
		}

		//caluclating the number of members for each family
		for (int i = 0; i < n; i++) {
			str = last[i];
			j = 0;

			for (k = 0; k < n; k++) {
				if (str.equals(last[k])) {
					j++;
				}
			}
			nrFamilyMembers[i] = j;
			if (j > maxj)
				maxj = j;
		}

		//copy of nrFamilyMembers
		for (int i = 0; i < n; i++) {
			copyNrFamilyMembers[i] = nrFamilyMembers[i];
		}


		//control will signal if that person was traversed or not
		for (j = maxj; j >= 0; j--) {
			control = 0;

			for (int i = 0; i < n; i++) {
				if (copyNrFamilyMembers[i] == j) {
					if (!listLastName.contains(last[i])) {
						control = 1;//we continue searching for families with j members
						listLastName.add(last[i]);
					}
				}

			}
			if (control == 1)
				j++;
		}//ascending ordered list of family names

		j = 0;
		for (String s : listLastName) {

			listFamiliiMenbri.add(new ArrayList<String>());
			listAux = listFamiliiMenbri.get(j);

			//initializing
			for (int i = 0; i < n; i++)
				markedPeople[i] = 0;


			for (int i = 0; i < n; i++) {
				k = 0;
				if (last[i].equals(s)) {
					if (markedPeople[i] == 0) {

						k = i;
						for (int m = i; m < n; m++) {
							if (dates[k].compareTo(dates[m]) > 0) {
								if (markedPeople[k] == 0)
									k = m;
							}
						}

						markedPeople[k] = 1;    //marked
						listAux.add(first[k]);    //adding
						if (markedPeople[i] == 0)
							i--;                //and continue with i not i++
					}
				}
			}

			j++;
		}

		j = 0;
		for (String s : listLastName) {
			System.out.print(s + ": ");

			listAux = listFamiliiMenbri.get(j);
			for (String se : listAux) {
				System.out.print(se + " ");
			}
			System.out.println();
			j++;
		}
	}
	/*	the following is input for testing
		6
		Mihai Enescu 1980-01-02
		George Ionescu 1992-06-20
		Maria Popescu 1995-03-13
		Elena Popescu 1990-12-13
		Andrei Ionescu 1996-03-01
		Sergiu Ionescu 1990-02-01

		8
		Mihai Enescu 1980-01-02
		George Ionescu 1992-06-20
		Maria Popescu 1995-03-13
		Elena Popescu 1990-12-13
		Andrei Ionescu 1996-03-01
		Sergiu Ionescu 1990-02-01
		Ana Maria 1990-02-13
		Oana Ciurdea 1992-06-12

		10
		Mihai Enescu 1980-01-02
		George Ionescu 1992-06-20
		Maria Popescu 1995-03-13
		Elena Popescu 1990-12-13
		Andrei Ionescu 1996-03-01
		Sergiu Ionescu 1990-02-01
		Ana Maria 1990-02-13
		Jordan Maria 1991-06-20
		Jordi Maria 1993-06-20
		Jore Maria 1989-06-20
		Oana Ciurdea 1992-06-12

		*/


}
