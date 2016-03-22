package iut.info63.vraifauxandroid.metier.database;

import java.util.ArrayList;
import java.util.List;

import iut.info63.vraifauxandroid.metier.Question;

/**
 * Created by Nawhal on 14/03/2016.
 */
public class FakeQuestionAccessor implements IQuestionGetter {

    private List<Question> questions;

    public FakeQuestionAccessor()
    {
        questions = new ArrayList<>();
        questions.add(new Question("Truman a été élu président des Etats-Unis en 1945.", true));
        questions.add(new Question("Roosevelt à été président des États Unis pendant 4 ans.", false));
        questions.add(new Question("Obama est républicain.", false));
        questions.add(new Question("La première université date du 17ème siècle.", false));
        questions.add(new Question("La carte du génome humain a été achevé en 2001.", true));
        questions.add(new Question("Einstein est né en 1879 en Allemagne.", true));
        questions.add(new Question("Dans le Monde de Némo, Némo est un poisson lune.", false));
        questions.add(new Question("Seules les moustiques femelles piquent.", true));
        questions.add(new Question("777 est divisible par 3.", false));
        questions.add(new Question("La température la plus froide enregistrée sur Terre est de -93.2°C.", true));
        questions.add(new Question("Un chat peut sauter jusqu'à 1m50 de hauteur.", true));
        questions.add(new Question("Oslo est la capitale de la Hongrie.", false));
        questions.add(new Question("Le détroit de Magellan est un passage maritime situé au sud du Mexique.", false));
        questions.add(new Question("\"Actually\" est la traduction anglaise pour \"actuellement\".", false));
        questions.add(new Question("La plus longue frontière terrestre séparant deux Etats est la frontière canado-américiaine.", true));
        questions.add(new Question("La prise de la Bastille a eu lieu en 1789.", true));
        questions.add(new Question("La tour Eiffel a été construite en 1901.", false));
        questions.add(new Question("La femme d'Homer Simpson s'appelle Marguerite.", false));
        questions.add(new Question("La capitale du Guatemala est Guatemala.", true));
        questions.add(new Question("Heathrow est le nom de la plus grande gare de Londres.", false));
        questions.add(new Question("Une crevette a 12 pattes.", false));
        questions.add(new Question("La Statue de la Liberté mesure plus de 91m de haut.", true));
        questions.add(new Question("La Chine est le premier producteur de soja au monde.", false));
        questions.add(new Question("Le vin existe depuis 8000 ans.", true));
        questions.add(new Question("Un melon d’eau est composé d’environ 55 % d’eau.", false));
        questions.add(new Question("Le miel peut se conserver plus de 1000 ans.", true));
        questions.add(new Question("Les connexions Wi-Fi peuvent être perturbées par les fours à micro-ondes.", true));
        questions.add(new Question("L’ananas fait maigrir.", false));
        questions.add(new Question("Le kiwi est originaire de la Chine.", true));
        questions.add(new Question("Pékin est la ville la plus peuplée au monde.", false));
        questions.add(new Question("La durée d'une journée a toujours été de 24h.", false));
        questions.add(new Question("McDonalds est le premier distributeur de jouets dans le monde.", true));
        questions.add(new Question("Une molécule d'eau est constituée de deux atomes d'hydrogène et d'un atome d'oxygène.", true));
        questions.add(new Question("Michael Jackson est né le 29 août 1988.", false));
        questions.add(new Question("World of Warcraft est un jeu vidéo où le protagoniste est un agriculteur.", false));
        questions.add(new Question("Moules-frites est un plat populaire de Suisse.", false));
        questions.add(new Question("Matrix est sorti en 1999.", true));
        questions.add(new Question("Le Japon forme un archipel de 6 852 îles de plus de 100 m².", true));
        questions.add(new Question("Steve Jobs, Steve Wozniak et Ronald Wayne ont fondé Apple.", true));
        questions.add(new Question("La FIFA est la Fondation Intercommunautaire des Femmes Afghanes.", false));
        questions.add(new Question("Jules César était un général, homme politique et écrivain grec.", false));
        questions.add(new Question("Sony Corporation une société multinationale japonaise.", true));
        questions.add(new Question("La pizza est un plat d'origine libanaise.", false));
        questions.add(new Question("Le cycle ovarien dure environ 28 jours.", true));
        questions.add(new Question("La nuit, les végétaux ne font pas la photosynthèse.", true));
        questions.add(new Question("La Géorgie est un État du Sud des États-Unis.", true));
        questions.add(new Question("Les deux Corées ont la même langue officielle : le coréen.", true));
        questions.add(new Question("L’allemand est la langue la plus parlée.", true));
        questions.add(new Question("Le Soudan est un pays d’Asie.", false));
        questions.add(new Question("La cerise est une plante de la famille des Cucurbitaceae.", false));
        questions.add(new Question("Le vin est une boisson alcoolisée obtenue par la fermentation de pommes.", false));
        questions.add(new Question("En 2011, l'entreprise Apple employait 60 400 employés.", true));
        questions.add(new Question("Madonna est surnommée The Queen of Pop (\"La Reine de la Pop\").", true));
        questions.add(new Question("Le mot « ordinateur » fut introduit par IBM France en 1955.", true));
        questions.add(new Question("Le mot chat vient du bas latin cattus.", true));
        questions.add(new Question("La durée du crépuscule dépend de la latitude de l'observateur.", true));
        questions.add(new Question("Le Texas est le deuxième plus vaste État des États-Unis.", true));
        questions.add(new Question("Le football se joue principalement au pied avec un ballon oval.", false));
        questions.add(new Question("Alfonso Pecoraro Scanio a été ministre de l'Agriculture en Espagne.", false));
        questions.add(new Question("Le Royaume de Danemark est composé de trois pays constitutifs.", true));
        questions.add(new Question("Le Danemark est un pays d’Europe du Nord.", true));
        questions.add(new Question("Nicolas Sarkozy était le 23e président de la République française.", true));
        questions.add(new Question("Jacques Maritain est un philosophe français.", true));
        questions.add(new Question("La durée de la nuit varie selon la saison et  selon l'endroit où l'on se situe.", true));
        questions.add(new Question("Pelophylax lessonae est un petit chien typique d’Asie du Sud.", false));
        questions.add(new Question("Mercedes-Benz est un constructeur d'automobiles italien.", false));
        questions.add(new Question("La Corée est une région d’Asie de l’Est de 223 348 km² située entre le Japon, la Chine et la Russie.", true));
        questions.add(new Question("Un ambassadeur est un représentant d'un État auprès d'un autre.", true));
        questions.add(new Question("Le Texas est plus grand que la France métropolitaine.", true));
        questions.add(new Question("Le Danemark est le plus petit des pays scandinaves.", true));
        questions.add(new Question("En 1997, Mattel a vendu sa milliardième poupée Barbie.", true));
        questions.add(new Question("En 2015, Rome est la seizième destination touristique la plus visitée d'Europe.", false));
        questions.add(new Question("Michael Jackson est mort le 25 juin 2009 à Sydney.", false));
        questions.add(new Question("La peau humaine se ride, s’amincit et perd son élasticité avec l’âge.", true));
        questions.add(new Question("Le crépuscule désigne le moment de la journée situé entre le jour et la nuit.", true));
        questions.add(new Question("\"Les Experts : Miami\" est une série télévisée en coproduction Canado-Australienne.", false));
        questions.add(new Question("Madona est une ville dans la région de la Vidzeme en Lettonie.", true));
        questions.add(new Question("Le chat est un prédateur de petites proies comme les rongeurs ou les oiseaux.", true));
        questions.add(new Question("Pac-Man est un jeu vidéo sorti au Japon le 22 mai 1980.", true));
    }

    @Override
    public Question getByIndex(int index) {
        return questions.get(index);
    }

    @Override
    public List<Question> getAll() {
        return questions;
    }

    @Override
    public void put(Question question) {
        questions.add(question);
    }

    @Override
    public void putAll(List<Question> questionList) {
        questions.addAll(questionList);
    }

    @Override
    public int count() { return questions.size(); }
}
