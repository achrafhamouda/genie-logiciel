const amqp = require('amqplib');

let channel;
const QUEUE = 'commandes';

async function connectRabbitMQ(retries = 5) {
  try {
    const conn = await amqp.connect('amqp://admin:admin@rabbitmq:5672');
    channel = await conn.createChannel();
    await channel.assertQueue(QUEUE, { durable: true });

    console.log('✅ RabbitMQ connecté');

  } catch (err) {
    console.log('⏳ RabbitMQ pas prêt, nouvelle tentative...');
    if (retries > 0) {
      setTimeout(() => connectRabbitMQ(retries - 1), 5000);
    } else {
      console.error('❌ Impossible de se connecter à RabbitMQ');
    }
  }
}

function publierCommande(commande) {
  if (!channel) {
    console.log('⚠️ RabbitMQ pas encore prêt');
    return;
  }

  const msg = JSON.stringify(commande);
  channel.sendToQueue(QUEUE, Buffer.from(msg), { persistent: true });
  console.log('📤 Message publié dans RabbitMQ');
}

module.exports = { connectRabbitMQ, publierCommande };