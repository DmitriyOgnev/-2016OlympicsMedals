import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class OlympicsMedals2016 {

	WebDriver driver;

	@BeforeClass
	public void setupClass() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("https://en.wikipedia.org/wiki/2016_Summer_Olympics#Medal_table");

	}

	@Test(priority = 1)
	public void testSortedByRank() {

		int numberOfRows = driver
				.findElements(By.xpath("//table[contains(@class,'wikitable')][contains(@class,'sortable')]"
						+ "[contains(@class,'plainrowheaders')][contains(@class,'jquery-tablesorter')]/tbody/tr"))
				.size();

		List<Integer> placesList = new ArrayList<Integer>();

		// Loops through the list of ros in the table
		for (int i = 1; i < numberOfRows; i++) {

			// Gets the place out of the 1st td in each tr
			int place = Integer.parseInt(
					driver.findElement(By.xpath("//table[contains(@class,'wikitable')][contains(@class,'sortable')]"
							+ "[contains(@class,'plainrowheaders')][contains(@class,'jquery-tablesorter')]/tbody/tr["
							+ i + "]/td[1]")).getText());

			placesList.add(place);
		}

		// Set<Integer> sortedPlaces = new TreeSet<Integer>(placesList);

		List<Integer> sortedPlaces = new ArrayList<Integer>(placesList);
		Collections.sort(sortedPlaces);

		// Asserts if the sorted and the original Lists are equal
		Assert.assertEquals(placesList, sortedPlaces);

	}

	@Test(priority = 2)
	public void testFindMostMedals() {
		int numberOfRows = driver
				.findElements(By.xpath("//table[contains(@class,'wikitable')][contains(@class,'sortable')]"
						+ "[contains(@class,'plainrowheaders')][contains(@class,'jquery-tablesorter')]/tbody/tr"))
				.size();

		List<String[]> countryMedals = new ArrayList<String[]>();

		// Loops through the list of ros in the table
		for (int i = 1; i < numberOfRows; i++) {

			// Gets the name of for each country
			String countryName = driver
					.findElement(By.xpath("//table[contains(@class,'wikitable')][contains(@class,'sortable')]"
							+ "[contains(@class,'plainrowheaders')][contains(@class,'jquery-tablesorter')]/tbody/tr["
							+ i + "]/th/a"))
					.getText();

			// Gets the medals of for each country
			String goldMedal = driver
					.findElement(By.xpath("//table[contains(@class,'wikitable')][contains(@class,'sortable')]"
							+ "[contains(@class,'plainrowheaders')][contains(@class,'jquery-tablesorter')]/tbody/tr["
							+ i + "]/td[2]"))
					.getText();

			String silverMedal = driver
					.findElement(By.xpath("//table[contains(@class,'wikitable')][contains(@class,'sortable')]"
							+ "[contains(@class,'plainrowheaders')][contains(@class,'jquery-tablesorter')]/tbody/tr["
							+ i + "]/td[3]"))
					.getText();

			String bronzeMedal = driver
					.findElement(By.xpath("//table[contains(@class,'wikitable')][contains(@class,'sortable')]"
							+ "[contains(@class,'plainrowheaders')][contains(@class,'jquery-tablesorter')]/tbody/tr["
							+ i + "]/td[4]"))
					.getText();

			String TotalMedal = driver
					.findElement(By.xpath("//table[contains(@class,'wikitable')][contains(@class,'sortable')]"
							+ "[contains(@class,'plainrowheaders')][contains(@class,'jquery-tablesorter')]/tbody/tr["
							+ i + "]/td[5]"))
					.getText();

			// Adds an array of country name and medals to a list
			countryMedals.add(new String[] { countryName, goldMedal, silverMedal, bronzeMedal, TotalMedal });
		}

		// // Prints the List of array records
		// for (String[] row : countryMedals) {
		// System.out.println(Arrays.toString(row));
		// }

		int topGoldCount = 0;
		String topGoldCountry = "";

		int topSilverCount = 0;
		String topSilverCountry = "";

		int topBronzeCount = 0;
		String topBronzeCountry = "";

		int topTotalCount = 0;
		String topTotalCountry = "";

		// Loops through each record in the list of Country X Medals
		for (int i = 0; i < countryMedals.size(); i++) {

			int medalCount = Integer.parseInt(countryMedals.get(i)[1]);

			if (medalCount > topGoldCount) {
				topGoldCountry = countryMedals.get(i)[0];
				topGoldCount = medalCount;
			}

			medalCount = Integer.parseInt(countryMedals.get(i)[2]);

			if (medalCount > topSilverCount) {
				topSilverCountry = countryMedals.get(i)[0];
				topSilverCount = medalCount;
			}

			medalCount = Integer.parseInt(countryMedals.get(i)[3]);

			if (medalCount > topBronzeCount) {
				topBronzeCountry = countryMedals.get(i)[0];
				topBronzeCount = medalCount;
			}

			medalCount = Integer.parseInt(countryMedals.get(i)[4]);

			if (medalCount > topTotalCount) {
				topTotalCountry = countryMedals.get(i)[0];
				topTotalCount = medalCount;
			}
		}

		// // Prints the number of highest medalist countries
		// System.out.println(topGoldCountry + " got " + topGoldCount + " medals.");
		// System.out.println(topSilverCountry + " got " + topSilverCount + " medals.");
		// System.out.println(topBronzeCountry + " got " + topBronzeCount + " medals.");
		// System.out.println(topTotalCountry + " got " + topTotalCount + " medals.");

		Assert.assertTrue(topGoldCountry.equals("United States") && topSilverCountry.equals("United States")
				&& topBronzeCountry.equals("United States") && topBronzeCountry.equals("United States"));
	}

	@Test(priority = 2)
	public void testFindMostMedals_2() {

		// Creates the List of Country names and medals
		List<String[]> countryMedals = getListCountryMedals();

		String topGoldCountry = getTopCoutry(countryMedals, "gold");
		String topSilverCountry = getTopCoutry(countryMedals, "silver");
		String topBronzeCountry = getTopCoutry(countryMedals, "bronze");
		String topTotalCountry = getTopCoutry(countryMedals, "total");

		Assert.assertTrue(topGoldCountry.equals("United States") && topSilverCountry.equals("United States")
				&& topBronzeCountry.equals("United States") && topBronzeCountry.equals("United States"));
	}

	@Test(priority = 3)
	public void testCountryByMedals() {

		List<String> expectedList = Arrays.asList("China", "France");
		List<String> actualList = CountryByMedals();

		Assert.assertEquals(expectedList, actualList);
	}

	@Test(priority = 4)
	public void testGetIndex() {

		String countryName = "Japan";

		int[] actualIndex = getIndex(countryName);
		int[] expectedIndex = new int[] { 6, 2 };

		Assert.assertEquals(actualIndex, expectedIndex);
	}

	public int[] getIndex(String name) {

		// 0th = row, 1st = column
		int[] index = new int[2];

		// Creates the List of Country names and medals
		List<String[]> countryMedals = getListCountryMedals();

		for (int i = 0; i < countryMedals.size(); i++) {

			String currentCountry = countryMedals.get(i)[0];

			if (name.equals(currentCountry)) {
				index = new int[] { i + 1, 2 };
				break;
			}
		}

		return index;
	}

	@Test(priority=5)
	public void	testGetSum() {
		
		List<String> expectedPair = Arrays.asList("Italy", "Australia");
		List<String> actualPair = getSum();
		
//		System.out.println(actualPair);
//		System.out.println(expectedPair);
		
		Assert.assertEquals(actualPair, expectedPair);
		
	}

	public List<String> getSum() {

		// Creates the List of Country names and medals
		List<String[]> countryMedals = getListCountryMedals();

		List<String> pairCountries = new ArrayList<>();

		for (int i = 0; i < countryMedals.size(); i++) {

			int count1 = Integer.parseInt(countryMedals.get(i)[3]);

			if (count1 < 18) {
				for (int j = 0; j < countryMedals.size(); j++) {
					int total = count1 + Integer.parseInt(countryMedals.get(j)[3]); 
					if (total == 18 && i!=j) {
						pairCountries.add(countryMedals.get(i)[0]);
						pairCountries.add(countryMedals.get(j)[0]);
						
						
						return pairCountries;
						
					}
				}
			}
		}

		return pairCountries;
	}

	public List<String> CountryByMedals() {

		// Creates the List of Country names and medals
		List<String[]> countryMedals = getListCountryMedals();

		List<String> medalistCountries = new ArrayList<>();

		// Loops through each record in the list of Country X Medals
		for (int i = 0; i < countryMedals.size(); i++) {

			int medalCount = Integer.parseInt(countryMedals.get(i)[2]);

			if (medalCount == 18) {
				medalistCountries.add(countryMedals.get(i)[0]);
			}
		}
		return medalistCountries;
	}

	public List<String[]> getListCountryMedals() {
		int numberOfRows = driver
				.findElements(By.xpath("//table[contains(@class,'wikitable')][contains(@class,'sortable')]"
						+ "[contains(@class,'plainrowheaders')][contains(@class,'jquery-tablesorter')]/tbody/tr"))
				.size();

		List<String[]> countryMedals = new ArrayList<String[]>();

		// Loops through the list of ros in the table
		for (int i = 1; i < numberOfRows; i++) {

			// Gets the name of for each country
			String countryName = driver
					.findElement(By.xpath("//table[contains(@class,'wikitable')][contains(@class,'sortable')]"
							+ "[contains(@class,'plainrowheaders')][contains(@class,'jquery-tablesorter')]/tbody/tr["
							+ i + "]/th/a"))
					.getText();

			// Gets the medals of for each country
			String goldMedal = driver
					.findElement(By.xpath("//table[contains(@class,'wikitable')][contains(@class,'sortable')]"
							+ "[contains(@class,'plainrowheaders')][contains(@class,'jquery-tablesorter')]/tbody/tr["
							+ i + "]/td[2]"))
					.getText();

			String silverMedal = driver
					.findElement(By.xpath("//table[contains(@class,'wikitable')][contains(@class,'sortable')]"
							+ "[contains(@class,'plainrowheaders')][contains(@class,'jquery-tablesorter')]/tbody/tr["
							+ i + "]/td[3]"))
					.getText();

			String bronzeMedal = driver
					.findElement(By.xpath("//table[contains(@class,'wikitable')][contains(@class,'sortable')]"
							+ "[contains(@class,'plainrowheaders')][contains(@class,'jquery-tablesorter')]/tbody/tr["
							+ i + "]/td[4]"))
					.getText();

			String TotalMedal = driver
					.findElement(By.xpath("//table[contains(@class,'wikitable')][contains(@class,'sortable')]"
							+ "[contains(@class,'plainrowheaders')][contains(@class,'jquery-tablesorter')]/tbody/tr["
							+ i + "]/td[5]"))
					.getText();

			// Adds an array of country name and medals to a list
			countryMedals.add(new String[] { countryName, goldMedal, silverMedal, bronzeMedal, TotalMedal });
		}

		// // Prints the List of array records
		// for (String[] row : countryMedals) {
		// System.out.println(Arrays.toString(row));
		// }

		return countryMedals;

	}

	public String getTopCoutry(List<String[]> countryMeds, String medal) {

		int topMedalCount = 0;
		String topMedalCountry = "";

		int medalNo = 0;

		switch (medal.toLowerCase()) {
		case "gold":
			medalNo = 1;
			break;
		case "silver":
			medalNo = 2;
			break;
		case "bronze":
			medalNo = 3;
			break;
		case "total":
			medalNo = 4;
			break;
		}

		// Loops through each record in the list of Country X Medals
		for (int i = 0; i < countryMeds.size(); i++) {

			int medalCount = Integer.parseInt(countryMeds.get(i)[medalNo]);

			if (medalCount > topMedalCount) {
				topMedalCountry = countryMeds.get(i)[0];
				topMedalCount = medalCount;
			}
		}

		return topMedalCountry;
	}

	@AfterClass
	public void teardownClass() {
		driver.close();

	}

}
