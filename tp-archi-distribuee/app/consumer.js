// app/consumer.js

const amqp = require('amqplib');

const QUEUE = 'commandes';

async function demarrerConsommateur() {
  let retries = 10;

  while (retries > 0) {
    try {
      const conn = await amqp.connect('amqp://admin:admin@rabbitmq');
      const channel = await conn.createChannel();

      await channel.assertQueue(QUEUE, { durable: true });

      console.log('👂 Consommateur en attente de messages...');

      channel.consume(QUEUE, (msg) => {
        if (msg) {
          const commande = JSON.parse(msg.content.toString());

          console.log('');
          console.log('🔔 NOUVELLE COMMANDE REÇUE !');
          console.log(' Client :', commande.client);
          console.log(' Produit :', commande.produit);
          console.log(' ID :', commande.id);
          console.log('📧 Email de confirmation envoyé (simulé)');
          console.log('');

          channel.ack(msg);
        }
      });

      return; // succès → on sort

    } catch (error) {
      console.log('⏳ RabbitMQ pas prêt, nouvelle tentative...');
      retries--;
      await new Promise(res => setTimeout(res, 3000));
    }
  }

  console.error('❌ Impossible de connecter le consumer à RabbitMQ');
}

demarrerConsommateur();