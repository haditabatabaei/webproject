


#pragma once

#include <functional>
#include <algorithm>
#include <vector>
#include <map>
#include <chrono>
#include <libethcore/CommonJS.h>
#include <libethereum/Transaction.h>

namespace dev
{
namespace eth
{

class KeyManager;
class Interface;

enum class TransactionRepercussion
{
	Unknown,
	UnknownAccount,
	Locked,
	Refused,
	ProxySuccess,
	Success
};

struct TransactionNotification
{
	TransactionRepercussion r;
	h256 hash;
	Address created;
};


class AccountHolder
{
public:
	explicit AccountHolder(std::function<Interface*()> const& _client): m_client(_client) {}
	virtual ~AccountHolder() = default;

	virtual AddressHash realAccounts() const = 0;
			virtual TransactionNotification authenticate(dev::eth::TransactionSkeleton const& _t) = 0;

	Addresses allAccounts() const;
	bool isRealAccount(Address const& _account) const { return realAccounts().count(_account) > 0; }
	bool isProxyAccount(Address const& _account) const { return m_proxyAccounts.count(_account) > 0; }
	Address const& defaultTransactAccount() const;

				virtual bool unlockAccount(
		Address const& ,
		std::string const& ,
		unsigned 
	)
	{
		return false;
	}

	int addProxyAccount(Address const& _account);
	bool removeProxyAccount(unsigned _id);
	void queueTransaction(eth::TransactionSkeleton const& _transaction);

	std::vector<eth::TransactionSkeleton> const& queuedTransactions(int _id) const;
	void clearQueue(int _id);

protected:
	std::function<Interface*()> m_client;

private:
	using TransactionQueue = std::vector<eth::TransactionSkeleton>;

	std::unordered_map<Address, int> m_proxyAccounts;
	std::unordered_map<int, std::pair<Address, TransactionQueue>> m_transactionQueues;
};

class SimpleAccountHolder: public AccountHolder
{
public:
	SimpleAccountHolder(std::function<Interface*()> const& _client, std::function<std::string(Address)> const& _getPassword, KeyManager& _keyman, std::function<bool(TransactionSkeleton const&, bool)> _getAuthorisation = std::function<bool(TransactionSkeleton const&, bool)>()):
		AccountHolder(_client),
		m_getPassword(_getPassword),
		m_getAuthorisation(_getAuthorisation),
		m_keyManager(_keyman)
	{}

	AddressHash realAccounts() const override;
	TransactionNotification authenticate(dev::eth::TransactionSkeleton const& _t) override;

	virtual bool unlockAccount(Address const& _account, std::string const& _password, unsigned _duration) override;

private:
	std::function<std::string(Address)> m_getPassword;
	std::function<bool(TransactionSkeleton const&, bool)> m_getAuthorisation;
	KeyManager& m_keyManager;
	std::map<Address, std::pair<std::chrono::steady_clock::time_point, unsigned>> m_unlockedAccounts;
};

class FixedAccountHolder: public AccountHolder
{
public:
	FixedAccountHolder(std::function<Interface*()> const& _client, std::vector<dev::KeyPair> const& _accounts):
		AccountHolder(_client)
	{
		setAccounts(_accounts);
	}

	void setAccounts(std::vector<dev::KeyPair> const& _accounts)
	{
		for (auto const& i: _accounts)
			m_accounts[i.address()] = i.secret();
	}

	dev::AddressHash realAccounts() const override
	{
		dev::AddressHash ret;
		for (auto const& i: m_accounts)
			ret.insert(i.first);
		return ret;
	}

			TransactionNotification authenticate(dev::eth::TransactionSkeleton const& _t) override;

private:
	std::unordered_map<dev::Address, dev::Secret> m_accounts;
};


}
}
